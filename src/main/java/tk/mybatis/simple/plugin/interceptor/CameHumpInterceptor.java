package tk.mybatis.simple.plugin.interceptor;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 就是循环判断结果。如果是 Map 类型的结果，就对 Map
 * 进行处理 处理时为了避免把己经是驼峰的值转换为纯小写，因此通过首字母是否为大写或是
 * 否包含下画线来判断（实际应用中要根据实际情况修改）。如果符合其中 个条件就转换为驼峰
 * 形式，删除对应的 ey 值，使用新的 ey 值来代替 。
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}))
public class CameHumpInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 先执行结果,再对结果进行处理
        List<Object> result = (List<Object>) invocation.proceed();
        for (Object object : result) {
            // 如果结果是 Map 类型的,就对 Map 的 key 进行转换
            if (object instanceof Map)
                processMap((Map) object);
            else
                break;
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * 处理 Map 类型
     *
     * @param map
     */
    private void processMap(Map<String, Object> map) {
        HashSet<String> keySet = new HashSet<>(map.keySet());
        for (String key : keySet) {
            // 将以大写开头的字符串转换为小写,如果包含下划线也会处理为驼峰
            // 此处只通过这两个简单的标识来判断是否进行转换
            if (key.charAt(0) >= 'A' && key.charAt(0) <= 'Z' && key.contains("_")) {
                Object value = map.get(key);
                map.remove(key);
                map.put(underlineToCamelHump(key), value);
            }
        }
    }

    /**
     * 将下划线风格替换为驼峰风格
     */
    private String underlineToCamelHump(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            if (c == '_') {
                if (stringBuilder.length() > 0)
                    nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    stringBuilder.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    stringBuilder.append(Character.toLowerCase(c));
                }
            }

        }
        return stringBuilder.toString();
    }
}
