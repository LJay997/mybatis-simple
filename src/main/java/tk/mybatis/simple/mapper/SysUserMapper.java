package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cache.decorators.FifoCache;
import tk.mybatis.simple.annotations.ReadOnly;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.List;
import java.util.Map;

//@CacheNamespaceRef(SysUserMapper.class)
/*同时使用注解方式和 映射文件时 如果同时配置了上述的 级缓存，就会抛出异常。*/
//@CacheNamespace(eviction = FifoCache.class, flushInterval = 60000, size = 512)
public interface SysUserMapper {
    SysUser selectAllUserAndRolesSelect(Long id);

    List<SysUser> selectAllUserAndRoles();

    SysUser selectUserAndRoleByIdSelect(Long id);

    SysUser selectUserAndRoleById2(Long id);

    SysUser selectUserAndRoleById(Long id);

    List<SysUser> selectAll();

    List<SysRole> selectRolesByUserId(@Param("userId") Long userId);

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKeyWithBLOBs(SysUser record);

    int updateByPrimaryKey(SysUser record);

    @ReadOnly
    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId, @Param("enabled") Integer enabled);

    List<SysRole> selectRolesByUserIdAndRoleEnabled1(@Param("sysUser") SysUser sysUser, @Param("sysRole") SysRole sysRole);

    SysUser selectByIdOrUserName(SysUser sysUser);

    List<SysUser> selectByIdList(@Param("idList") List<Long> idList);

    // 批量获取自动生成Key 不能再使用 @Param 标注  foreach collection=“名字”
    // 需要根据 入参类型 而改变  list - List类型、 collection - Collection类型  _parameter - Map类型
    int insertList(List<SysUser> userList);

    // TODO 标记位置 可能遇到特殊的jdbcType会出错
    int updateSysUserByMap(@Param("param1") Map<String, Object> map, @Param("param") SysUser sysUser);
}