package tk.mybatis.simple.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@MappedTypes(MyEnum.class)
//@MappedJdbcTypes() 此处不指定两种类型 建议在 resultMap 中使用 typeHandler="" 前指明 javaType jdbcType="VARCHAR"
public class MyTypeHandler<E> extends BaseTypeHandler<EnumInterface> {

    private final Map<Integer, MyEnum> enumMap = new HashMap<>();
    // 初始化时候加载 MyEnum 所有数据
    public MyTypeHandler() {
        for (MyEnum myEnum : MyEnum.values()) {
            enumMap.put(myEnum.getValue(),myEnum);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EnumInterface parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,parameter.getValue());
    }

    @Override
    public EnumInterface getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return enumMap.get(value);
    }

    @Override
    public EnumInterface getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return enumMap.get(value);
    }

    @Override
    public EnumInterface getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return enumMap.get(value);
    }
}