package event;

/*
 * 事件源依赖中间的时间管理器
 */
public class EventTest {
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        eventManager.register("click", new ClickListener());
        eventManager.register("change", new ChangeListener());
        // 事件管理器预先注册事件到具体的监听器，事件源依赖事件管理器
        Button button = new Button(eventManager);
        // 事件源发送事件
        button.click(); // 仅ClickListener响应
        button.changeState(); // 仅ChangeListener响应
    }
}