package event;

// 具体监听器（处理点击事件）
class ClickListener implements EventListener {
    @Override
    public void onEvent(Event event) {
        if ("click".equals(event.getType())) {
            System.out.println("处理点击事件：" + event.getData());
        }
    }
}