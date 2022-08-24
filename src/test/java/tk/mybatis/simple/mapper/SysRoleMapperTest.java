package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.plugin.interceptor.PageRowBounds;
import tk.mybatis.simple.type.Enable;

import java.util.Date;
import java.util.List;

public class SysRoleMapperTest extends BaseMapperTest {

    @Test
    public void testSelectAllByRowBounds() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            RowBounds rowBounds = new RowBounds(0, 1);
            List<SysRole> sysRoleList = sysRoleMapper.selectAll(rowBounds);
            for (SysRole sysRole : sysRoleList) {
                System.out.println("角色名：" + sysRole.getRoleName());
            }
            // 使用 PageRowBounds 时会查询总数
            PageRowBounds pageRowBounds = new PageRowBounds(0, 1);
            sysRoleList = sysRoleMapper.selectAll(pageRowBounds);
            System.out.println("查询总数：" + pageRowBounds.getTotal());
            for (SysRole sysRole : sysRoleList) {
                System.out.println("角色名：" + sysRole.getRoleName());
            }

            // 再次查询 获取第二个角色
            pageRowBounds = new PageRowBounds(1, 1);
            sysRoleList = sysRoleMapper.selectAll(pageRowBounds);
            System.out.println("查询总数：" + pageRowBounds.getTotal());
            for (SysRole sysRole : sysRoleList) {
                System.out.println("角色名：" + sysRole.getRoleName());
            }
        }

    }

    @Test
    public void testUpdateById() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            SysRole sysRole = sysRoleMapper.selectById2(1L);
            sysRole.setEnabled(Enable.disable);
            sysRoleMapper.updateById(sysRole);
        }
    }

    @Test
    public void testSelectRoleByUserIdChoose() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            SysRole sysRole = sysRoleMapper.selectById(2L);
//            sysRole.setEnabled(0);
            // 由于数据库数据 enable 都是 ，所以给其中一个角色的 enable 赋值为 0
            sysRoleMapper.updateByPrimaryKeySelective(sysRole);
            List<SysRole> sysRoles = sysRoleMapper.selectRoleByUserIdChoose(1L);
            for (SysRole role : sysRoles) {
                System.out.println("角色名:" + role.getRoleName());
                if (role.getId().equals(1L)) {
                    // 第一个角色存在权限信息
                    Assert.assertNotNull(role.getSysPrivilegeList());
                } else if (role.getId().equals(2L)) {
                    // 第二个角色权限为null
                    Assert.assertNull(role.getSysPrivilegeList());
                    continue;
                }
                for (SysPrivilege sysPrivilege : role.getSysPrivilegeList()) {
                    System.out.println("权限名:" + sysPrivilege.getPrivilegeName());
                }
            }
        }
    }

    @Test
    public void testSelectAllRoleAndPrivileges() {
        SqlSession sqlSession = getSqlSession();
        SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);

        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(1L);
        System.out.println(sysRole);
    }

    @Test
    public void testInsert1() {
        SqlSession sqlSession = getSqlSession();
        SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
        SysRole sysRole = new SysRole(null, "Jay", 1L, new Date(), null);
        int result = sysRoleMapper.insert1(sysRole);
        Assert.assertEquals(1, result);
        System.out.println("sysRole.getId() = " + sysRole.getId());
    }

    @Test
    public void testSelectById() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            SysRole sysRole = sysRoleMapper.selectById(1L);
            System.out.println("sysRole = " + sysRole);
        }

    }

    @Test
    public void testSelectById2() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            SysRole sysRole = sysRoleMapper.selectById2(1L);
            System.out.println("sysRole = " + sysRole);
        }
    }
}