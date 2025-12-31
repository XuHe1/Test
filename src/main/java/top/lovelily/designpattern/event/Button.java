package top.lovelily.designpattern.event;

// 事件发布者（无需知道监听器）
class Button {
    private EventManager eventManager;

    public Button(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    // 点击按钮时触发事件
    public void click() {
        eventManager.trigger(new Event("click", this, "按钮被点击了"));
    }

    // 状态变更时触发事件
    public void changeState() {
        eventManager.trigger(new Event("change", this, "按钮状态变更了"));
    }
}