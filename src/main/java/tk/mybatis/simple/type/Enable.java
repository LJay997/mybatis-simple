package tk.mybatis.simple.type;

//使用它的方式是在MyBatis配置文件中定义
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
