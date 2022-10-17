package tk.mybatis.simple.plugin.interceptor;

import org.apache.commons.lang.ClassUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.simple.annotations.ReadOnly;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 *  测试方法 tk.mybatis.simple.mapper.SysUserMapper#selectRolesByUserIdAndRoleEnabled(java.lang.Long, java.lang.Integer)<br/>
 *   tk.mybatis.simple.mapper.SysUserMapper#selectRolesByUserIdAndRoleEnabled1(tk.mybatis.simple.model.SysUser, tk.mybatis.simple.model.SysRole)
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DataSourceSwitchInterceptor implements Interceptor {

    /**
     * 可以识别为 添加、更新、删除类型的 SQL 语句
     */
    public static final List<SqlCommandType> UPDATE_SQL_LIST = Arrays.asList(SqlCommandType.INSERT, SqlCommandType.UPDATE, SqlCommandType.DELETE);

    /**
     * SQL 语句中出现的乐观锁标识
     */
    private static final String LOCK_KEYWORD = " for update";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 通过 invocation 获取 MappedStatement 与 拦截方法的形参信息
        Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement) objects[0];

        // 通过反射检查要执行的方法
        String clazzStr = ms.getId().substring(0, ms.getId().lastIndexOf("."));
        String methodStr = ms.getId().substring(ms.getId().lastIndexOf(".") + 1);
        MapperMethod.ParamMap object = (MapperMethod.ParamMap) objects[1];
        Class[] paramType = new Class[object.size() / 2];
        for (int i = 1; i <= object.size() / 2; i++) {
            paramType[i - 1] = object.get("param" + i).getClass();
        }
        Method mapperMethod = ClassUtils.getClass(clazzStr).getMethod(methodStr, paramType);

        // 获取 sqlCommandType
        SqlCommandType sqlCommandType = ms.getSqlCommandType();

        // 格式化 SQL
        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replace("[\\t\\n\\r]", " ");


        // 如果 SqlCommandType 是 INSERT, UPDATE, DELETE
        // 或者使用了数据库乐观锁 "*** for update"
        // 或者 mapperMethod 上标注了 @ReadOnly --则使用写数据源
        if (mapperMethod.getAnnotation(ReadOnly.class) != null && sqlCommandType.equals(SqlCommandType.SELECT)) {
            System.out.println("使用主库-读数据源");
        } else if (UPDATE_SQL_LIST.contains(sqlCommandType) || sql.contains(LOCK_KEYWORD)) {
            System.out.println("使用主库-写数据源");
        } else {
            // 使用从库-多元库
            System.out.println("使用从库-多源库数据源");
        }
        //继续执行逻辑
        Object proceed;
        try {
            proceed = invocation.proceed();
        } catch (Throwable t) {
            throw t;
        }
        return proceed;
    }

    @Override
    public Object plugin(Object o) {
        //获取代理权
        if (o instanceof Executor) {
            //如果是Executor（执行增删改查操作），则拦截下来
            return Plugin.wrap(o, this);
        } else {
            return o;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        //读取mybatis配置文件中属性
    }
}
