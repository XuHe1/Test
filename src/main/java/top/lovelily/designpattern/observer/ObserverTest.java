package top.lovelily.designpattern.observer;

public class ObserverTest {
    public static void main(String[] args) {
        Subject subject = new Subject();
        subject.attach(new ConcreteObserver("观察者A"));
        subject.attach(new ConcreteObserver("观察者B"));
        
        subject.setState("状态更新了！"); // 所有观察者收到通知
    }
}