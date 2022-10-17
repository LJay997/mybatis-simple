package tk.mybatis.simple.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import tk.mybatis.simple.type.SysRoleIDDBEnum;
import tk.mybatis.simple.type.SysRoleIntDBEnum;
import tk.mybatis.simple.type.SysRoleUserTypeEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_role
 *
 * @author Jay
 */
@Data
@AllArgsConstructor
// 使用可读写缓存，可以使用 SerializedCache(org.apache.ibatis.cache.decorators.SerializedCache) 序列缓存。这个缓存类要求所有
//被序列化的对象必须实现 Serializable (java.io.Serializable ）接口
public class SysRole implements Serializable {
    /**
     * 角色ID
     */
//    private Long id;
    private SysRoleIDDBEnum id;
    /**
     * 角色名
     */
    private SysRoleUserTypeEnum roleName;

    /**
     * 有效标志
     */
//    private Integer enabled;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建信息
     */
    private CreateInfo createInfo;

//    public Enable getEnabled() {
//        return enabled;
//    }

//    public void setEnabled(Enable enabled) {
//        this.enabled = enabled;
//    }

    /**
     * 有效标志
     */
//    private Enable enabled;
    /**
     * 有效标志
     */
    private SysRoleIntDBEnum enabled;

    public SysRoleIntDBEnum getEnabled() {
        return enabled;
    }

    public void setEnabled(SysRoleIntDBEnum enabled) {
        this.enabled = enabled;
    }

    /**
     * 用户信息
     */
    private SysUser user;

    private List<SysPrivilege> sysPrivilegeList;

    public List<SysPrivilege> getSysPrivilegeList() {
        return sysPrivilegeList;
    }

    public void setSysPrivilegeList(List<SysPrivilege> sysPrivilegeList) {
        this.sysPrivilegeList = sysPrivilegeList;
    }

    private static final long serialVersionUID = 1L;

    public SysRole() {
    }

    public SysRole(SysRoleIDDBEnum id, SysRoleUserTypeEnum roleName, Long createBy, Date createTime, SysUser user) {
        this.id = id;
        this.roleName = roleName;

        this.createBy = createBy;
        this.createTime = createTime;
        this.user = user;
    }

}