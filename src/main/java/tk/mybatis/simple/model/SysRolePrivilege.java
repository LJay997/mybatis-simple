package tk.mybatis.simple.model;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_role_privilege
 * @author Jay
 */
@Data
public class SysRolePrivilege implements Serializable {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID
     */
    private Long privilegeId;

    private static final long serialVersionUID = 1L;
}