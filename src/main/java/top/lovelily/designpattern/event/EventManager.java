package event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 事件管理器（事件分发中心）
class EventManager {
    // 按事件类型存储监听器
    private Map<String, List<EventListener>> listeners = new HashMap<>();

    // 注册监听器到指定事件类型
    public void register(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    // 触发事件
    public void trigger(Event event) {
        List<EventListener> eventListeners = listeners.get(event.getType());
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.onEvent(event);
            }
        }
    }
}