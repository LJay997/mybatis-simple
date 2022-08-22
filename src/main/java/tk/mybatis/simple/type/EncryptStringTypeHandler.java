package tk.mybatis.simple.type;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 识别不了嵌套的association； BaseTypeHandler 与 TypeHandler 的区别
@MappedTypes(String.class)
public class EncryptStringTypeHandler extends BaseTypeHandler<String> {

    private static final byte[] KEYS = "12345678abcdefgh".getBytes(StandardCharsets.UTF_8);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null){
            ps.setString(i,null);
        }
        AES aes = SecureUtil.aes(KEYS);
        String s = aes.encryptHex(parameter);
        ps.setString(i,s);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        return SecureUtil.aes(KEYS).decryptStr(string);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String string = rs.getString(columnIndex);
        return SecureUtil.aes(KEYS).decryptStr(string);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String string = cs.getString(columnIndex);
        return SecureUtil.aes(KEYS).decryptStr(string);
    }

}
