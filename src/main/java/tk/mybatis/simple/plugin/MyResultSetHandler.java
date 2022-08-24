package tk.mybatis.simple.plugin;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class MyResultSetHandler implements ResultSetHandler {

    /**
     * 在 除 存储过程 及 返回值类型为 Cursor<T> 以外的查询方法中被调用(3.4.0 版本中新增)
     */
    @Override
    public <E> List<E> handleResultSets(Statement stmt) throws SQLException {
        return null;
    }


    @Override
    public <E> Cursor<E> handleCursorResultSets(Statement stmt) throws SQLException {
        return null;
    }
    /**
     * 只在使用存储过程处理出参时被调用
     */
    @Override
    public void handleOutputParameters(CallableStatement cs) throws SQLException {

    }
}
