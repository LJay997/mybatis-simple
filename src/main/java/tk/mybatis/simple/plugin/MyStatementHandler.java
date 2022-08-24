package tk.mybatis.simple.plugin;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class MyStatementHandler implements StatementHandler {
    /**
     * 该方法会在数据库执行前被调用 优先于当前接口中的其他方法而被执行
     */
    @Override
    public Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException {
        return null;
    }

    /**
     * 该方法在 prepare 方法之后执行，用于处理参数信息
     */
    @Override
    public void parameterize(Statement statement) throws SQLException {

    }

    /**
     * 在全局设置配置 defaultExecutorType BATCH 时，执行数据操作才会调用该方
     */
    @Override
    public void batch(Statement statement) throws SQLException {

    }

    @Override
    public int update(Statement statement) throws SQLException {
        return 0;
    }

    /**
     * 执行 SELECT 方法时调用
     */
    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        return null;
    }

    /**
     * 只会在返回值类型为 Cursor<T ＞的查询中被调用
     */
    @Override
    public <E> Cursor<E> queryCursor(Statement statement) throws SQLException {
        return null;
    }

    @Override
    public BoundSql getBoundSql() {
        return null;
    }

    @Override
    public ParameterHandler getParameterHandler() {
        return null;
    }
}
