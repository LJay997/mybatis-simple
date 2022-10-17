package tk.mybatis.simple.type;

public enum SysRoleIDDBEnum implements BaseIntDBEnum {

    FIRST(1, "第一名"),
    SECOND(2, "第二名"),
    THREE(3, "第三名");


    Integer value;

    String label;

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    SysRoleIDDBEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 因为 TypeHandler 的三个 get 方法返回值是 BaseIntDBEnum
     * 如果不重新 toString 方法。则展示值 为 Enum 的 name（DISABLE、ENABLE）
     *
     * @return 禁用\启用
     */
    @Override
    public String toString() {
        return label;
    }
}