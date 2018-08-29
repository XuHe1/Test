package com.etianxia.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author h.xu
 * @create 2017-09-18 下午4:06
 **/
public class TestLock {

    // 读写锁
    private ReadWriteLock readAndWrite = new ReentrantReadWriteLock();

    private float[] accounts = {100f, 50f};

    // LinkedBlockingQueue 实现原理就是使用了ReentrantLock
    // 引申：ConcurrentLinkedQueue 使用CAS保证并发性,CAS是在硬件层面实现的原子操作
    private Lock bankLock = new ReentrantLock(); // ReentrantLock implements the Lock interface ...

    // Condition 锁
    private Condition condition = bankLock.newCondition();

    public void transfer(int from, int to, int amount) {
        bankLock.lock();
        try {
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %d from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bankLock.unlock();
        }
    }

    private float getTotalBalance() {
        try {
            bankLock.lock();
            return accounts[0];
        } finally {
            bankLock.unlock();
        }

    }

    public static void main(String[] args) {

        final TestLock bank = new TestLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    bank.transfer(0, 1, 10);
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                bank.transfer(0, 1, 20);
            }
        }).start();
    }
}
