package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;

public class SysPrivilegeMapperTest extends BaseMapperTest {

    @Test
    public void selectById() {
        try (SqlSession sqlSession = getSqlSession()) {
            SysPrivilegeMapper sysPrivilegeMapper = sqlSession.getMapper(SysPrivilegeMapper.class);
            SysPrivilege sysPrivilege = sysPrivilegeMapper.selectById(1L);
            System.out.println(sysPrivilege);
        }
    }
}