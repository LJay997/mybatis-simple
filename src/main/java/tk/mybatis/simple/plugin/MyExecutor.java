package tk.mybatis.simple.plugin;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

public abstract class MyExecutor implements Executor {
    /**
     *  该方法会在所有的 INSERT UPDATE DELET 执行时被调用，
     */
    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        return 0;
    }

    /**
     * 该方法不可被拦截
     */
    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException {
        return null;
    }

    /**
     * 该方法会在所有 SELECT 查询方法执行时被调用
     */
    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        return null;
    }

    /**
     * 该方法只有在查询 的返回值类型为 Cursor 时被调用
     */
    @Override
    public <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException {
        return null;
    }

    /**
     * 该方法只在通过 SqlSession 方法调用 flushStatements 方法或执行的接口方法中带有＠Flush 注解时才被调用
     */
    @Override
    public List<BatchResult> flushStatements() throws SQLException {
        return null;
    }

    /**
     * 只在通过 SqlSession 方法调用 commit 方法时才被调用
     */
    @Override
    public void commit(boolean required) throws SQLException {

    }

    /**
     * 过 SqlSession口方法调用 rollback 方法时才被调用
     */
    @Override
    public void rollback(boolean required) throws SQLException {

    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
        return null;
    }

    @Override
    public boolean isCached(MappedStatement ms, CacheKey key) {
        return false;
    }

    @Override
    public void clearLocalCache() {

    }

    @Override
    public void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType) {

    }

    /**
     * 在通过 SqlSession 方法获取数据库连接时才被调用，
     */
    @Override
    public Transaction getTransaction() {
        return null;
    }

    /**
     * 只在延迟加载获取新的 Executor 后才会被执行
     */
    @Override
    public void close(boolean forceRollback) {

    }

    /**
     * 该方法只在延迟加载执行查询方法前被执行
     */
    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void setExecutorWrapper(Executor executor) {

    }
}
