package tk.mybatis.simple.type;

public interface EnumInterface {

    /**
     *  获取枚举的 value
     * @return
     */
    int getValue();

    /**
     *  获取枚举的 label
     * @return
     */
    String getLabel();

    /**
     *  获取枚举的 注释
     * @return
     */
    String getComment();
}
