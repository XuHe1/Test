package top.lovelily.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

// 主题（被观察者）
class Subject {
    private List<Observer> observers = new ArrayList<>();
    private String state;

    // 注册观察者
    public void attach(Observer observer) {
        observers.add(observer);
    }

    // 移除观察者
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    // 状态变更时通知所有观察者
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(state);
        }
    }

    // 设置状态并触发通知，同步触发调用
    public void setState(String state) {
        this.state = state;
        notifyObservers();
    }
}