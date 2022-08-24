package tk.mybatis.simple.plugin.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * MySQL实现
 */
@SuppressWarnings("rawtypes")
public class MySqlDialect implements Dialect {
    @Override
    public boolean skip(String msId, Object parameterObject, RowBounds rowBounds) {
        //这里使用 RowBounds 分页
        //没有 RowBounds 参数时，会使用 RowBounds.DEFAULT 作为默认值
        return rowBounds == RowBounds.DEFAULT;
    }

    @Override
    public boolean beforeCount(String msId, Object parameterObject, RowBounds rowBounds) {
        // 只有使用 PageRowBounds 才能记录总数，否则查询了总数也没用
        return rowBounds instanceof PageRowBounds;
    }

    @Override
    public String getCountSql(BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey countKey) {
        // 简单嵌套实现 MySQL count 查询
        return "select count(*) from ( " + boundSql.getSql() + ") temp";
    }

    @Override
    public void afterCount(long count, Object parameterObject, RowBounds rowBounds) {
        // 记录总数，按照 beforeCount 逻辑，只有 PageRowBounds 才会查询 count,
        // 所以这里直接强制转换
        ((PageRowBounds) rowBounds).setTotal(count);
    }

    @Override
    public boolean beforePage(String msId, Object parameterObject, RowBounds rowBounds) {
        return rowBounds != RowBounds.DEFAULT;
    }

    @Override
    public String getPageSql(BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey pageKey) {
        // pageKey 会影响缓存，通过固定的 RowBounds 可以保证二级缓存有效
        pageKey.update("RowBounds");
        return boundSql.getSql() + " limit " + rowBounds.getOffset() + "," + rowBounds.getLimit();
    }

    @Override
    public Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds) {
        // 直接返回查询结果
        return pageList;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
