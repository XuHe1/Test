package observer;

// 具体观察者
class ConcreteObserver implements Observer {
    private String name;
    public ConcreteObserver(String name) { this.name = name; }
    
    @Override
    public void update(String message) {
        System.out.println(name + " 收到通知：" + message);
    }
}