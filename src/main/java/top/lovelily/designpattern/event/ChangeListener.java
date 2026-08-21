package top.lovelily.designpattern.event;

// 具体监听器（处理变更事件）
class ChangeListener implements EventListener {
    @Override
    public void onEvent(Event event) {
        if ("change".equals(event.getType())) {
            System.out.println("处理变更事件：" + event.getData());
        }
    }
}