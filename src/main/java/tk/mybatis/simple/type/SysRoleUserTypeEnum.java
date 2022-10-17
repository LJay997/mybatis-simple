package tk.mybatis.simple.type;

public enum SysRoleUserTypeEnum implements BaseStringDBEnum {
    ONE("admin", "管理员"),
    TWO("user", "普通用户");

    String value;

    String label;

    SysRoleUserTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
