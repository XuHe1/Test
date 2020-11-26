package top.lovelily.wheel;

import java.util.LinkedList;

/**
 * Desc: TestDBCP
 * Author: xuhe
 * Date: 2020/11/26 上午11:18
 * Version: 1.0
 */
public class ConnectionPool {
    class Connection {

    }
    // 链表存放连接， 通过唤醒通知wait-notify处理连接不可用及获取超时问题
    private volatile LinkedList<Connection> pool = new LinkedList<>();
    private int size;
    // 初始化连接池
    public ConnectionPool(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            pool.add(new Connection());
        }
    }

    // 从连接池获取连接
    public Connection getConnection(long timeMills) {
        synchronized (pool) {
            System.out.println("enter");
                if (timeMills <= 0) {
                    try {
                        pool.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 超时时间点
                long future = System.currentTimeMillis() + timeMills;
                long remaining = timeMills;
                // 循环获取，直到超时
                while (pool.isEmpty() && remaining > 0) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " 等待可用连接...");
                        pool.wait(remaining); // 线程阻塞，等待唤醒
                        remaining = future - System.currentTimeMillis();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 不能放在while上面，否则不会执行！！
                if (pool.size() > 0) {
                    Connection connection = pool.removeFirst();
                    System.out.println(Thread.currentThread().getName() + " get Connection " + connection );
                    return connection;
                }

                if (remaining <= 0) {
                    System.out.println("暂无可用连接！");
                    return null;
                }

        }
        return null;

    }

    // 释放连接，归还到连接池
    public void releaseConnection(Connection connection) {
        if (pool.size() < size) {
            synchronized (pool) {
                pool.addLast(connection);
                pool.notifyAll(); // 唤醒阻塞的获取连接的线程
            }
        }
    }

    public static void main(String[] args) {
            ConnectionPool connectionPool = new ConnectionPool(10);
            for (int i = 0; i < 11; i++) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection connection = connectionPool.getConnection(100l);
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        connectionPool.releaseConnection(connection);
                    }
                });
                t.start();
            }


        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
