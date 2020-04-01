package top.lovelily.concurrent;

import top.lovelily.designpattern.composite.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc: TestFairLock
 * Author: xuhe
 * Date: 2020/3/30 10:23 下午
 * Version: 1.0
 */
public class TestFairLock {
    private boolean           isLocked       = false;
    private Thread            lockingThread  = null;
    private List<QueueObject> waitingThreads =
            new ArrayList<QueueObject>();

    public void lock() throws InterruptedException{
        QueueObject queueObject           = new QueueObject();
        boolean     isLockedForThisThread = true;
        synchronized(this){
            waitingThreads.add(queueObject);
        }

        while(isLockedForThisThread){
            synchronized(this){
                isLockedForThisThread =
                        isLocked || waitingThreads.get(0) != queueObject;
                if(!isLockedForThisThread){
                    isLocked = true;
                    waitingThreads.remove(queueObject);
                    lockingThread = Thread.currentThread();
                    return;
                }
            }
            try{
                queueObject.doWait();
            }catch(InterruptedException e){
                synchronized(this) { waitingThreads.remove(queueObject); }
                throw e;
            }
        }
    }

    public synchronized void unlock(){
        if(this.lockingThread != Thread.currentThread()){
            throw new IllegalMonitorStateException(
                    "Calling thread has not locked this lock");
        }
        isLocked      = false;
        lockingThread = null;
        if(waitingThreads.size() > 0){
            waitingThreads.get(0).doNotify();
        }
    }

    public static void main(String[] args) {
        TestFairLock fairLock = new TestFairLock();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fairLock.lock();
                    Thread.sleep(10000l);
                    System.out.println("thread1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    fairLock.unlock();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fairLock.lock();
                    Thread.sleep(5000l);
                    System.out.println("thread2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    fairLock.unlock();
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}

class QueueObject {

    private boolean isNotified = false;

    public synchronized void doWait() throws InterruptedException {
        while(!isNotified){
            this.wait();
        }
        this.isNotified = false;
    }

    public synchronized void doNotify() {
        this.isNotified = true;
        this.notify();
    }

    public boolean equals(Object o) {
        return this == o;
    }
}