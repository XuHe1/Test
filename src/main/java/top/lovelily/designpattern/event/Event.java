package top.lovelily.designpattern.event;

// 事件对象（通信载体）
class Event {
    private String type; // 事件类型
    private Object source; // 事件源
    private String data; // 事件数据

    public Event(String type, Object source, String data) {
        this.type = type;
        this.source = source;
        this.data = data;
    }

    // getter方法
    public String getType() { return type; }
    public String getData() { return data; }
}
