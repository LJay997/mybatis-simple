package tk.mybatis.simple.model;

import lombok.Data;
import org.apache.ibatis.jdbc.SQL;


import java.io.Serializable;

/**
 * sys_privilege
 *
 * @author Jay
 */
@Data
public class SysPrivilege implements Serializable {
    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限名称
     */
    private String privilegeName;

    /**
     * 权限URL
     */
    private String privilegeUrl;

    private static final long serialVersionUID = 1L;

    /**
     * Provider
     */
    public String selectById(final Long id) {
        return new SQL() {{
            SELECT("id, privilege_name, privilege_url");
            FROM("sys_privilege");
            WHERE("id= #{id, jdbcType=BIGINT }");
        }}.toString();
/* 1       return "select id, privilege_name, privilege_url\n" +
                "from sys_privilege\n" +
                "where id = #{id ,jdbcType=BIGINT}";*/
    }
}