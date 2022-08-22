package tk.mybatis.simple.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import tk.mybatis.simple.model.SysUser;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CountryMapperTest {
	
	private static SqlSessionFactory sqlSessionFactory;
	
	@BeforeClass
	public static void init() throws IOException {
		System.out.println(System.getProperty("file.encoding"));
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		reader.close();
	}
	
	@Test
	public void testSelectAll(){
		try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
			List<SysUser> countryList = sqlSession.selectList("tk.mybatis.simple.mapper.SysUserMapper.selectAll");
			printCountryList(countryList);
		}
	}
	
	private void printCountryList(List<SysUser> countryList){
		for(SysUser sysUser : countryList){
			System.out.printf("%-4d%4s%4s\n",sysUser.getId(), sysUser.getUserEmail(),sysUser.getUserInfo());
		}
	}
}
