package tk.mybatis.simple.type;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Enable类型处理器
 */
public class EnableTypeHandler implements TypeHandler<Enable> {

    private final Map<Integer, Enable> enableHashMap = new HashMap<Integer, Enable>();

    public EnableTypeHandler() {
        for (Enable enable : Enable.values()) {
            enableHashMap.put(enable.getValue(), enable);
        }
    }

    public EnableTypeHandler(Class<?> type) {
        this();
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Enable parameter, JdbcType jdbcType) throws SQLException {
        // 预编译(可变)SQL
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public Enable getResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return enableHashMap.get(value);
    }

    @Override
    public Enable getResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return enableHashMap.get(value);
    }

    @Override
    public Enable getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return enableHashMap.get(value);
    }
}
