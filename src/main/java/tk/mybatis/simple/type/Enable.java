package tk.mybatis.simple.type;

public enum Enable {
    disable(0), // 禁用0
    enable(1); // 启用1
    private final int value;

    public int getValue() {
        return value;
    }

    Enable(int value) {
        this.value = value;
    }
}
