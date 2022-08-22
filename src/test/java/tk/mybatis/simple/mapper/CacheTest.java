package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.Arrays;

public class CacheTest extends BaseMapperTest {

    @Test
    public void testL1Cache() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        SysUser user1;
        try {
            //获取 SysUserMapper 接口
            SysUserMapper SysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            //调用 selectByPrimaryKey 方法，查询 id = 1 的用户
            user1 = SysUserMapper.selectByPrimaryKey(1L);
            //对当前获取的对象重新赋值
            user1.setUserName("New Name");
            //再次查询获取 id 相同的用户
            SysUser user2 = SysUserMapper.selectByPrimaryKey(1L);
            //虽然我们没有更新数据库，但是这个用户名和我们 user1 重新赋值的名字相同了
            Assert.assertEquals("New Name", user2.getUserName());
            //不仅如何，user2 和 user1 完全就是同一个实例
            Assert.assertEquals(user1, user2);
        } finally {
            //关闭当前的 sqlSession
            sqlSession.close();
        }
        System.out.println("开启新的 sqlSession");
        //开始另一个新的 session
        sqlSession = getSqlSession();
        try {
            //获取 SysUserMapper 接口
            SysUserMapper SysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            //调用 selectByPrimaryKey 方法，查询 id = 1 的用户
            SysUser user2 = SysUserMapper.selectByPrimaryKey(1L);
            //第二个 session 获取的用户名仍然是 admin
            Assert.assertNotEquals("New Name", user2.getUserName());
            //这里的 user2 和 前一个 session 查询的结果是两个不同的实例
            Assert.assertNotEquals(user1, user2);
            //执行删除操作
            SysUserMapper.deleteByPrimaryKey(2L);
            //获取 user3
            SysUser user3 = SysUserMapper.selectByPrimaryKey(1L);
            //这里的 user2 和 user3 是两个不同的实例
            Assert.assertNotEquals(user2, user3);
        } finally {
            //关闭 sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testL2Caches() {
        SysRole sysRole1 = null;
        try (SqlSession sqlSession = getSqlSession()) {
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            sysRole1 = sysRoleMapper.selectByPrimaryKey(1L);
            sysRole1.setRoleName("New Name");
            SysRole sysRole2 = sysRoleMapper.selectByPrimaryKey(1L);
            Assert.assertEquals("New Name", sysRole2.getRoleName());
            Assert.assertEquals(sysRole1, sysRole2);
        }
        System.out.println("开启新的sqlSession");

        try (SqlSession sqlSession = getSqlSession()) {
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            SysRole sysRole2 = sysRoleMapper.selectByPrimaryKey(1L);
            sysRole2.setRoleName("New Name");
            Assert.assertEquals(sysRole1, sysRole2);

            SysRole sysRole3 = sysRoleMapper.selectByPrimaryKey(1L);
            Assert.assertEquals("New Name", sysRole2.getRoleName());
            Assert.assertEquals(sysRole3, sysRole2);
        }

    }

    @Test
    public void testL2Cache() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        SysRole role1;
        try {
            //获取 SysRoleMapper 接口
            SysRoleMapper SysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            //调用 selectByPrimaryKey 方法，查询 id = 1 的用户
            role1 = SysRoleMapper.selectByPrimaryKey(1L);
            //对当前获取的对象重新赋值
            role1.setRoleName("New Name");
            //再次查询获取 id 相同的用户
            SysRole role2 = SysRoleMapper.selectByPrimaryKey(1L);
            //虽然我们没有更新数据库，但是这个用户名和我们 role1 重新赋值的名字相同了
            Assert.assertEquals("New Name", role2.getRoleName());
            //不仅如何，role2 和 role1 完全就是同一个实例
            Assert.assertNotEquals(role1, role2);
        } finally {
            //关闭当前的 sqlSession
            sqlSession.close();
        }
        System.out.println("开启新的 sqlSession");
        //开始另一个新的 session
        sqlSession = getSqlSession();
        try {
            //获取 SysRoleMapper 接口
            SysRoleMapper SysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            //调用 selectByPrimaryKey 方法，查询 id = 1 的用户
            SysRole role2 = SysRoleMapper.selectByPrimaryKey(1L);
            //第二个 session 获取的用户名仍然是 admin
            Assert.assertEquals("New Name", role2.getRoleName());
            //这里的 role2 和 前一个 session 查询的结果是两个不同的实例
            Assert.assertNotEquals(role1, role2);
            //获取 role3
            SysRole role3 = SysRoleMapper.selectByPrimaryKey(1L);
            //这里的 role2 和 role3 是两个不同的实例
            Assert.assertNotEquals(role2, role3);
        } finally {
            //关闭 sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testDirtyData() {
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper SysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUser user = SysUserMapper.selectUserAndRoleById(1001L);
            Assert.assertEquals("普通用户", user.getSysRoleList().get(0).getRoleName());

        } finally {
            sqlSession.close();
        }
        //开始另一个新的 session
        sqlSession = getSqlSession();
        try {
            SysRoleMapper SysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            SysRole role = SysRoleMapper.selectByPrimaryKey(2L);
            role.setRoleName("脏数据");
            SysRoleMapper.updateById(role);
            //提交修改
            sqlSession.commit();
        } finally {
            //关闭当前的 sqlSession
            sqlSession.close();
        }
        System.out.println("开启新的 sqlSession");
        //开始另一个新的 session
        sqlSession = getSqlSession();
        try {
            SysUserMapper SysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysRoleMapper SysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            SysUser user = SysUserMapper.selectUserAndRoleById(1001L);
            SysRole role = SysRoleMapper.selectByPrimaryKey(2L);
            Assert.assertEquals("普通用户", user.getSysRoleList().get(0).getRoleName());
            Assert.assertEquals("脏数据", role.getRoleName());
            System.out.println("角色名：" + user.getSysRoleList().get(0).getRoleName());
            //还原数据
            role.setRoleName("普通用户");
            SysRoleMapper.updateById(role);
            //提交修改
            sqlSession.commit();
        } finally {
            //关闭 sqlSession
            sqlSession.close();
        }
    }
}
