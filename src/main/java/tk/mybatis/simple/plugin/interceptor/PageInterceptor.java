package tk.mybatis.simple.plugin.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PageInterceptor implements Interceptor {

    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<>(0);

    private Dialect dialect;

    private Field additionalParametersField;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取拦截方法的参数
        Object[] args = invocation.getArgs();

        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];

        // 调用方法判断是否需要进行分页，如果不需要，直接返回结果
        if (!dialect.skip(ms.getId(), parameter, rowBounds)) {
            // 获取当前的目标对象
            Executor executor = (Executor) invocation.getTarget();
            BoundSql boundSql = ms.getBoundSql(parameter);
            // 反射获取动态参数
            Map<String, Object> additionalParameters = (Map<String, Object>) additionalParametersField.get(boundSql);

            // 判断是否需要进行 count 查询
            if (dialect.beforeCount(ms.getId(), parameter, rowBounds)) {
                // 根据当前的 ms 创建一个返回值为 Long 类型的 ms
                MappedStatement countMs = newMappedStatement(ms, Long.class);
                // ／创建 count 查询的缓存 key
                CacheKey countCacheKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
                // 调用方言过去 count sql
                String countSql = dialect.getCountSql(boundSql, parameter, rowBounds, countCacheKey);

                BoundSql countBoundSql = new BoundSql(
                        ms.getConfiguration(),
                        countSql,
                        boundSql.getParameterMappings(),
                        parameter
                );

                // 当使用动态 SQL 时，可能会产生临时的参数
                // 这些参数需要手动设置到新的 BoundSQL 中
                for (String key : additionalParameters.keySet()) {
                    countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
                }

                // 执行 count 查询
                List<Object> countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countCacheKey, countBoundSql);
                Long count = (Long) countResultList.get(0);
                // 处理查询总数
                dialect.afterCount(count, parameter, rowBounds);
                if (count == 0L) {
                    // 当查询总条数为 0 时，直接返回空的结果
                    return dialect.afterPage(new ArrayList(), parameter, rowBounds);
                }
            }
            //判断是否需要进行分页查询
            if (dialect.beforePage(ms.getId(), parameter, rowBounds)) {
                // 生成分页的缓存 key
                CacheKey cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
                // 调用方言获取分页 sql
                String pageSql = dialect.getPageSql(boundSql, parameter, rowBounds, cacheKey);
                BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(), pageSql, boundSql.getParameterMappings(), parameter);
                // 设置动态参数
                for (String key : additionalParameters.keySet()) {
                    pageBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
                }
                // 执行分页查询
                List<Object> resultList = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
                return dialect.afterPage(resultList, parameter, rowBounds);
            }
        }
        // 返回默认查询
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String dialectClass = properties.getProperty("dialect");
        try {
            dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("使用 PageInterceptor 分页插件时，必须设置 dialect 属性");
        }
        dialect.setProperties(properties);
        try {
            // 反射获取 BoundSql 中的 additionalParameters 属性
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据现有的 ms 创建一个新的返回值类型，使用新的返回值类型
     *
     * @param ms
     * @param resultType
     * @return
     */
    private MappedStatement newMappedStatement(MappedStatement ms, Class<?> resultType) {
        MappedStatement.Builder builder = new MappedStatement.Builder(
                ms.getConfiguration(),
                ms.getId() + "_Count",
                ms.getSqlSource(),
                ms.getSqlCommandType()
        );
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());

        if (ms.getKeyGenerator() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());

        // count 查询返回值 int
        ArrayList<ResultMap> resultMaps = new ArrayList<>();
        ResultMap resultMap = new ResultMap.Builder(
                ms.getConfiguration(),
                ms.getId(),
                resultType,
                EMPTY_RESULTMAPPING
        ).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());


        return builder.build();
    }
}
