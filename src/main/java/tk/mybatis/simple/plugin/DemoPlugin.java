package tk.mybatis.simple.plugin;

import org.apache.ibatis.plugin.*;

import java.util.Properties;

//@Signature @Intercepts
public abstract class DemoPlugin implements Interceptor {
    /**
     * 先加载的拦截器会优先被遍历, 会优先对目标对象进行代理,
     * 后加载的拦截器, 在原有的代理之上再进行代理,
     * 当方法被调用前, 会先执行最外层（后加载）的代理方法.
     * 当方法被调用后,会先执行先加载（最里层）的代理方法。
     * 所以, 先加载到的拦截器, 反而是最后执行
     * <p>
     * 运行时要执行的拦截方法
     *
     * @param invocation
     * @return
     * @throws Throwable 异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    /**
     * 只需要调用 MyBatis 提供的 Plugin 的warp
     * Plugin.wrap 方法会自动判断拦截器的签名和被拦截对象的接口是否匹配，
     * 只有匹配的情况下才会使用动态代理拦截目标对象
     *
     * @param target 拦截器要拦截的对象
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 用来传递插件参数
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
