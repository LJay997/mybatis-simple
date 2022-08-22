package tk.mybatis.simple.mapper;

import tk.mybatis.simple.model.SysRolePrivilege;

public interface SysRolePrivilegeMapper {
    int insert(SysRolePrivilege record);

    int insertSelective(SysRolePrivilege record);
}