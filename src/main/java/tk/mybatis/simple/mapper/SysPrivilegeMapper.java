package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.simple.model.SysPrivilege;

import java.util.List;

public interface SysPrivilegeMapper {
    List<SysPrivilege> selectPrivilegeByRoleId (Long roleId);
    int deleteByPrimaryKey(Long id);

    int insert(SysPrivilege record);

    int insertSelective(SysPrivilege record);

    SysPrivilege selectByPrimaryKey(Long id);

    @SelectProvider(type = SysPrivilege.class,method = "selectById")
    SysPrivilege selectById(Long id);

    int updateByPrimaryKeySelective(SysPrivilege record);

    int updateByPrimaryKey(SysPrivilege record);
}