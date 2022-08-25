## 第三章 MyBatis 注解方式的基本用法

在 MyBatis 注解 SQL 中，最基本的就是@ Select 、@ Insert 、@Update 和@Delete 四
种 。 

 4 种 Provider 注解，分别是@SelectProvider 、＠ InsertProvider 、＠ Update Provider 和＠ DeleteProvider 。 它
们同样可以实现查询、插入、更新、删除操作。

## 第四章 MySQL动态SQL

 MyBatis采用了功能强大的 OGNL ( Object-Graph Navigation Language ）表达式语言消除了许多其他标签，
以下是 MyBatis 的动态 SQL 在 XML 中支持的几种标签。if 、choose、 trim（where set）、foreach、bind

OGNL用法

1. el or e2 
2. el and e2 
3. el == e2 或 el eq e2 
4.  el ! = e2 或 el neq e2 
5. el lt e2 ：小于
6. el lte e2 ：小于等于，其他表示为 gt （大于）、 gte （大于等于）
7. el + e2 、 e l 食 e2 、 e 1/e2 、 e 1 - e2 、 e l 宅 e2
8. ! e 或 not e ：非，取反
9. e.method(args ） ： 调用对象方法
10. e.property ： 对象属性值
11. el[ e2 ］ ： 按索引取值（ List 、数组和 Map)
12. **@class@method(args ）：调用类的静态方法**
13. **@class@f ield ：调用类的静态字段值**

## 第五章 Mybatis 代码生成器

​	http ://www.mybatis.org/generator

​	https://github.com/makejavas/EasyCode

## 第六章 Mybatis高级查询

### 	高级结果映射

​			一对一\一对多 \  **鉴别器**

有时 一 个单独的数据库查询会返回很多不同数据类型（希望有些关联〉的结果集。discriminator 鉴别器标签就是用来处理这种情况的 。 鉴别器非常容易理解，因为它很像J ava 语言中的 switch 语句。
**discriminator 标签**常用的两个属性如下。
column ： 该属性用于设置要进行鉴别比较值的列 。
javaType ： 该属性用于指定列的类型，保证使用相同的 Java 类型来比较值。

discriminator 标签**可以有 l 个或多个 case 标签**， case 标签包含以下三个属性。
value ： 该值为 discriminator 指定 column 用来匹配的值 。
resultMap ： 当 column 的值和 value 的值匹配时，可以配置使用 resultMap 指定的映射， resultMap 优先级高于 result Type 。
resultType ： 当 column 的值和 value 的值匹配时，用于配置使用 result Typ e 指定的映射。
**case 标签下面可以包含的标签和 resultMap 一样**，用法也一样 。

### 	存储过程

**

### 	使用 My Batis 提供的枚举处理器

​		**利用TypeHandler实现**

​		**对 Java 8 日期（ JSR-310 ）的支持**(https://github.com/mybatis/typehandlers-jsr310 )

继承 BaseTypeHandler<T ＞类,间接继承了 TypeReference<T ＞类。由于 TypeReference<T ＞带有泛型类型， MyBatis 会对继承了 TypeReference<T ＞的类进行特殊处理，获取这里指定的泛型类型作为 javaType 属性，因此在配置的时候就不需要指定 javaType了 。



## 第七章 MyBatis 缓存配置

### 	一级缓存

MyBatis 的一级缓存**存在于 SqlSession 的生命周期中**，在同一个 SqlSession 中查询时， MyBatis 会把执行的方法和参数通过算法生成缓存的键值，将键值和查询结果存入一个 Map对象中。如果同一个 SqlSession 中执行的方法和参数完全一致，那么通过算法会生成相同的键值，当 Map 缓存对象中己经存在该键值时，**则会返回缓存中的对象**。

### 	二级缓存

存在于 Sq lSessionFactory 的生命周期中.

mybatis-config.xml

```xml
<settings> 
	<setting name="cacheEnabled" value="true" />
</settings> 
```

**Mapper.xml

```xml
<mapper namespace="tk.mybatis.simple.mapper.SysRoleMapper">
    <!--通常情况下每 Mapper 映射文件都拥有 自己的 级缓存，不同 Mapper 级缓存互不影 。
    脏数据的产生：关系型数据库的设计，使得很多时候需要关联多个表才能获得想要的数据。该查询放到某一个命名空间下的映射文件中
    这样一个多表的查询就会缓存在该命名空间的二级缓存中。涉及这些表的增、删、改操作通常不在同个映射文件中，它们的命名空间不同，
    当有数据变 时，多表查询的缓存未必会被清空。-->
    <!--3.使用 RedisCache-->
    <cache type="org.mybatis.caches.redis.RedisCache" />
    <!-- 2.使用 EhcacheCache 作为缓存-->
    <!--    <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>-->
    <!-- 1.让RoleMapper接口中的注解方法和 SysUserMapper.XML 中的方法使用相同的缓存2 -->
    <!--    <cache-ref namespace="tk.mybatis.simple.mapper.SysUserMapper"/>-->
    <!--    <cache eviction="FIFO" readOnly="true" flushInterval="60000" size="512"/>-->
</mapper>
```

与其他中间件的配合 

[MyBatis (github.com)](https://github.com/mybatis?q=cache&type=all&language=&sort=stargazers)

在选择使用 二级缓存前一定要认真考虑脏读对系统的影响。在任何情况下，都可以考虑在业务层使用可控制的缓存来代替二级缓存 。

## 第八章 MyBatis插件开发(拦截器)

可拦截四个接口的部分方法, [MyBatis 拦截器原理和使用方法_如来神掌十八式的博客-CSDN博客_mybatis拦截器使用](https://blog.csdn.net/t194978/article/details/124542336)

```Java
Executor // CachingExecutor

StatementHandler // RoutingStatementHandler

ResultSetHandler // DefaultParameterHandler

ParameterHandler // DefaultResultSetHandler
```