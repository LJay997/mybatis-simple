package tk.mybatis.simple.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 规范应该写 @MappedJdbcTypes
//@MappedJdbcTypes(value = JdbcType.TINYINT, includeNullJdbcType = true)
@MappedTypes(value = {SysRoleIntDBEnum.class ,SysRoleIDDBEnum.class})
// 实现DisplayedEnum 都需要写进去
// 例如：<typeHandler handler="EnableTypeHandler" javaType="SysRoleIntDBEnum"/>
// <typeHandler handler="EnableTypeHandler" javaType="SysRoleIDDBEnum"/>
public class DefaultEnumTypeHandler extends BaseTypeHandler<BaseIntDBEnum> {

    private Class<BaseIntDBEnum> type;

    public DefaultEnumTypeHandler() {
        System.out.println("init DefaultEnumTypeHandler no args");
        this.type = BaseIntDBEnum.class;
    }

    public DefaultEnumTypeHandler(Class<BaseIntDBEnum> type) {
        System.out.println("init DefaultEnumTypeHandler with args");
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseIntDBEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public BaseIntDBEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getInt(columnName));
    }

    @Override
    public BaseIntDBEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getInt(columnIndex));
    }

    @Override
    public BaseIntDBEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getInt(columnIndex));
    }

    private BaseIntDBEnum convert(int status) {
        BaseIntDBEnum[] objs = type.getEnumConstants();
        for (BaseIntDBEnum em : objs) {
            if (em.getValue() == status) {
                return em;
            }
        }
        return null;
    }
}