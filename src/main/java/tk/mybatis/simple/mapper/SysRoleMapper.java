package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.*;
import tk.mybatis.simple.model.SysRole;

import java.util.List;

//让RoleMapper接口中的注解方法和 SysUserMapper.XML 中的方法使用相同的缓存1
//@CacheNamespaceRef(.class)
public interface SysRoleMapper {

    List<SysRole> selectRoleByUserIdChoose(Long userId);

    List<SysRole> selectRoleByUserId(Long userId);

    List<SysRole> selectAllRoleAndPrivileges();

    int deleteByPrimaryKey(Long id);

    // 若不开启驼峰转换且sql查询没有使用别名 可以复用 Results
    @ResultMap("role")
    @Select({"select id, role_name, enabled, create_by, create_time \n",
            " from sys_role \n",
            " where id = #{id}"})
    SysRole selectById(Long id);

    // 需要将 pom 中 mybatis 升级为 3.3.1
    @Results(id = "role", value = {
            @Result(property = "id", column = "id", id = true), // 是否为主键
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select({"select id, id, role_name, enabled, create_by, create_time \n",
            " from sys_role \n",
            " where id = #{id}"})
    SysRole selectById2(Long id);

    // Options 中设置了使用自动生成的key 并将自动生成的值赋给JavaBean 的 id 属性中(keyProperty = "id")
    @Options(useGeneratedKeys = true)
    @Insert({"insert into sys_role (role_name, enabled, create_by, create_time) \n",
            " values (#{roleName,jdbcType=VARCHAR}, #{enabled,jdbcType=INTEGER}, #{createBy,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP})"})
    int insert1(SysRole sysRole);

    int insert(SysRole record);

    int insert3(SysRole record);

    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    @Insert({"insert into sys_role (role_name, enabled, create_by, create_time) \n",
            " values (#{roleName,jdbcType=VARCHAR}, #{enabled,jdbcType=INTEGER}, #{createBy,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP})"})
    int insert31(SysRole sysRole);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    @Update({" update sys_role\n",
            "   set role_name   = #{roleName,jdbcType=VARCHAR},",
            "   enabled     = #{enabled,jdbcType=INTEGER},",
            "   create_by   = #{createBy,jdbcType=BIGINT},",
            "   create_time = #{createTime,jdbcType=TIMESTAMP}\n",
            "   where id = #{id,jdbcType=BIGINT}"})
    int updateById(SysRole sysRole);

    @Delete({"delete \n",
            "from sys_role \n",
            "where id = #{id,jdbcType=BIGINT}"})
    int deleteById(Long id);
}