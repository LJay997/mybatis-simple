package tk.mybatis.simple.plugin;

import org.apache.ibatis.executor.parameter.ParameterHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class MyParameterHandler implements ParameterHandler {
    /**
     * 法只在执行存储过程处理出参的时候被调用。
     */
    @Override
    public Object getParameterObject() {
        return null;
    }

    /**
     * 所有数据库方法设置 SQL 参数时被调用。
     */
    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {

    }
}
