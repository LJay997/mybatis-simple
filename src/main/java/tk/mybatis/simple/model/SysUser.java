package tk.mybatis.simple.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.simple.type.Encrypt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_user
 * @author Jay
 */
@Data
public class SysUser implements Serializable {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 简介
     */
    private String userInfo;

    /**
     * 创建时间
     */
    private Date createTime;

//    private Encrypt userPassword1;

    /**
     * 头像
     */
    private byte[] headImg;

    private static final long serialVersionUID = 1L;

    private List<SysRole> sysRoleList;

    public List<SysRole> getSysRoleList() {
        return sysRoleList;
    }

    public void setSysRoleList(List<SysRole> sysRoleList) {
        this.sysRoleList = sysRoleList;
    }

    public SysUser(Long id, String userName, String userPassword, String userEmail, String userInfo, Date createTime, byte[] headImg) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userInfo = userInfo;
        this.createTime = createTime;
        this.headImg = headImg;
    }

    public SysUser() {
    }
}