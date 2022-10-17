package tk.mybatis.simple.type;

public enum MyEnum implements EnumInterface {

    ONE(1,"启用",""),
    TWO(2,"禁用","");

    // key
    private int value;
    // value
    private String label;
    // 备注
    private String comment;

    MyEnum(int value, String label, String comment) {
        this.value = value;
        this.label = label;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return this.label;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getComment() {
        return this.comment;
    }
}
