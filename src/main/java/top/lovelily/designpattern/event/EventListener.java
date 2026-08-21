package top.lovelily.designpattern.event;

// 事件监听器接口（事件处理者）
interface EventListener {
    void onEvent(Event event);
}