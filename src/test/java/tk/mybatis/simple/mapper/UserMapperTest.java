package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;
import tk.mybatis.simple.type.Encrypt;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UserMapperTest extends BaseMapperTest {

    @Test
    public void testSelectAllUserAndRolesSelect() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
            SysUser sysUserList = mapper.selectAllUserAndRolesSelect(1L);
            System.out.println(sysUserList);
        }
    }

    @Test
    public void testSelectAllUserAndRoles() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
            List<SysUser> sysUserList = mapper.selectAllUserAndRoles();
            for (SysUser sysUser : sysUserList) {
                System.out.print("用户名：" + sysUser.getUserName() + "\t");
                for (SysRole sysRole : sysUser.getSysRoleList()) {
                    System.out.print("角色名:" + sysRole.getRoleName() + "\t");
                    for (SysPrivilege sysPrivilege : sysRole.getSysPrivilegeList()) {
                        System.out.print("权限名：" + sysPrivilege.getPrivilegeName() + "\t");
                    }
                }
                System.out.println(" ");
            }
        }
    }

    //     在使用 association select 时候需要去掉 parameterType="java.lang.Long"
    // TODO fetchType 属性待验证
    @Test
    public void testSelectUserAndRoleByIdSelect() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
            SysUser sysUser = mapper.selectUserAndRoleByIdSelect(1001L);
            System.out.println(sysUser);
//            System.out.println(sysUser.getSysRole());
        }
    }

    @Test
    public void testSelectUserAndRoleById2() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
            SysUser sysUser = mapper.selectUserAndRoleById2(1001L);
            System.out.println(sysUser);
        }
    }

    @Test
    public void testSelectUserAndRoleById() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
            SysUser sysUser = mapper.selectUserAndRoleById(1001L);
            System.out.println(sysUser);
        }
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setUserName(null);
            sysUser.setUserPassword(" ");
            sysUser.setUserEmail(" ");
            sysUser.setUserInfo(" ");
            sysUser.setCreateTime(null);
            sysUser.setHeadImg(null);
            sysUser.setId(1L);
            int i = mapper.updateByPrimaryKeySelective(sysUser);
            Assert.assertEquals(1, i);
            sqlSession.commit();
        }

    }

    // TODO 待解决
    @Test
    public void testUpdateSysUserByMap() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("user_name", "s");
            hashMap.put("id", 1L);
            SysUser sysUser = new SysUser();
            sysUser.setUserPassword("123456");
            sysUser.setId(1L);
            int i = sysUserMapper.updateSysUserByMap(hashMap, sysUser);

            Assert.assertEquals(1, i);
        }
    }

    @Test
    public void testInsertList() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUser sysUser1 = new SysUser(null, "张三", "1234", "123@qq.com", "info", new Date(), new byte[]{1, 2, 3, 4, 5});
            SysUser sysUser2 = new SysUser(null, "张三1", "1234", "123@qq.com", "info", new Date(), new byte[]{1, 2, 3, 4, 5});
            SysUser sysUser3 = new SysUser(null, "张三2", "1234", "123@qq.com", "info", new Date(), new byte[]{1, 2, 3, 4, 5});
            SysUser sysUser4 = new SysUser(null, "张三3", "1234", "123@qq.com", "info", new Date(), new byte[]{1, 2, 3, 4, 5});
            List<SysUser> sysUserList = Arrays.asList(sysUser1, sysUser2, sysUser3, sysUser4);
            int i = sysUserMapper.insertList(sysUserList);
            Assert.assertEquals(4, i);
            sysUserList.forEach(sysUser -> System.out.println(sysUser.getId()));
        }
    }

    @Test
    public void testSelectByIdList() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            List<SysUser> sysUserList = sysUserMapper.selectByIdList(Arrays.asList(1001L, 1037L, 1038L));

            sysUserList.forEach(sysUser ->
                    System.out.println(sysUserList));
        }
    }

    @Test
    public void testSelectByIdOrUserName() {
        SqlSession sqlSession = getSqlSession();
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUser sysUser = new SysUser();
// 1       SysUser sysUser = sysUserMapper.selectByIdOrUserName(new SysUser());
//        sysUser.setId(1L);
        sysUser.setUserName("admin");
        SysUser sysUserNew = sysUserMapper.selectByIdOrUserName(sysUser);
        System.out.println(sysUserNew);
    }

    @Test
    public void testSelectRolesByUserIdAndRoleEnabled1() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setId(1L);
            SysRole sysRole = new SysRole();
//            sysRole.setEnabled(1);
            List<SysRole> sysRoles = mapper.selectRolesByUserIdAndRoleEnabled1(sysUser, sysRole);
            Assert.assertTrue(sysRoles.size() > 0);
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRolesByUserIdAndRoleEnabled() {
        SqlSession sqlSession = getSqlSession();
        SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
        List<SysRole> sysRoles = mapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
        Assert.assertTrue(sysRoles.size() > 0);
    }

    @Test
    public void testSelectById() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            List<SysUser> sysUserList = sysUserMapper.selectAll();
            Assert.assertNotNull(sysUserList);
            Assert.assertTrue(sysUserList.size() > 0);
        }
    }

    @Test
    public void testSelectRolesByUserId() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            List<SysRole> sysRoleList = sysUserMapper.selectRolesByUserId(1L);
            Assert.assertNotNull(sysRoleList);
            Assert.assertTrue(sysRoleList.size() > 0);
        }
    }

    @Test
    public void testInsert() {
        SqlSession sqlSession = getSqlSession();
        int insert;
        SysUser sysUser;
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);

            sysUser = new SysUser();
            sysUser.setUserName("test1");
            Encrypt encrypt = new Encrypt();
            encrypt.setValue("123456");
//            sysUser.setUserPassword1(encrypt);
            sysUser.setUserEmail("test@qq.com");
            sysUser.setUserInfo("test info");
            sysUser.setHeadImg(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});
            sysUser.setCreateTime(new Date());

            insert = sysUserMapper.insert(sysUser);
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
        System.out.println("sysUser.getId() = " + sysUser.getId());
        Assert.assertEquals(1, insert);
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(1068L);
            Assert.assertEquals("admin", sysUser.getUserName());
            sysUser.setUserName("admin_test");
            sysUser.setUserEmail("testFool");

            int i = sysUserMapper.updateByPrimaryKey(sysUser);
            Assert.assertEquals(1, i);

            SysUser sysUserNew = sysUserMapper.selectByPrimaryKey(1L);
            Assert.assertEquals("admin_test", sysUserNew.getUserName());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }

    }

    @Test
    public void testSelectByPrimaryKeyUesTypeHandler(){
        SqlSession sqlSession = getSqlSession();
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(1068L);
        System.out.println(sysUser);
    }

}