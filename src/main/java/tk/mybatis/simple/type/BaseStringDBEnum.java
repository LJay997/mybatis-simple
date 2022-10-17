package tk.mybatis.simple.type;

/**
 *  枚举应该包含 属性 Integer value 、String label;
 */
public interface BaseStringDBEnum {
    String getValue();

    String getLabel();

    String toString();
}
