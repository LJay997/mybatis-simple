package tk.mybatis.simple.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({SysRoleUserTypeEnum.class})
public class DefaultStringEnumTypeHandler extends BaseTypeHandler<BaseStringDBEnum> {

    private Class<BaseStringDBEnum> type;

    public DefaultStringEnumTypeHandler() {
        System.out.println("init DefaultStringEnumTypeHandler no args");
        this.type = BaseStringDBEnum.class;
    }

    public DefaultStringEnumTypeHandler(Class<BaseStringDBEnum> type) {
        System.out.println("init DefaultStringEnumTypeHandler with args");
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseStringDBEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public BaseStringDBEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getString(columnName));
    }

    @Override
    public BaseStringDBEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getString(columnIndex));
    }

    @Override
    public BaseStringDBEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getString(columnIndex));
    }

    private BaseStringDBEnum convert(String value) {
        BaseStringDBEnum[] enumConstants = type.getEnumConstants();
        for (BaseStringDBEnum dbEnum : enumConstants) {
            if (dbEnum.getValue() == value) {
                return dbEnum;
            }
        }
        return null;
    }
}