package top.lovelily.wheel;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 造轮子： CustomerThreadPool
 * Author: xuhe
 * Date: 2019/6/19 11:19 AM
 * Version: 1.0
 */
public class CustomerThreadPool {
    private int coreSize;
    private int maxPoolSize;
    private long keepAliveTime;
    private TimeUnit timeUnit;
    private LinkedBlockingDeque<Runnable> taskQueue;
    private AtomicInteger runningCount = new AtomicInteger(0); // 正在运行的线程数
    private Set<Thread> workers = new HashSet<>(); // 线程池中运行的线程，运行完毕的需要remove掉
    private ThreadGroup group = new ThreadGroup("CustomerThreadPool");
    private final ReentrantLock mainLock = new ReentrantLock();


    public CustomerThreadPool(int coreSize, int maxPoolSize, long keepAliveTime, TimeUnit timeUnit, LinkedBlockingDeque workerQueue) {
        this.coreSize = coreSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.taskQueue = workerQueue;
    }


    private Runnable getTask() {
        // 多线程取会有问题，添加锁
        try {
            mainLock.lock();
            if (workers.size() > coreSize) {
                return taskQueue.poll(keepAliveTime, timeUnit);
            } else {
                return taskQueue.take(); // 阻塞核心线程
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mainLock.unlock();
        }
        return null;
    }

    /**
     * 1. < coreSize: new
     * 2. 添加到队列
     * 3. 队列已满，新建线程
     * 4. >=maxPoolSize, reject
     *
     */
    public void execute(Runnable task) {

        if (workers.size() < coreSize) {
            addWorker(task);
        } else if (!taskQueue.offer(task)) {
            addWorker(task);
            if (runningCount.get() >= maxPoolSize) {
                throw new TaskRejectedException("任务拒绝！");
            }
        }

    }


    class Worker extends Thread {
        private Runnable firstTask;
        Worker(Runnable task) {
            super(group, task);
            this.firstTask = task;
        }
        @Override
        public void run() {
            // 线程不停执行任务，直到所有任务执行完毕
            try {
                while (firstTask != null || (firstTask = getTask()) != null) {
                    System.out.println(firstTask);
                    try {
                        firstTask.run();
                    } finally {
                        firstTask = null; // 保证每个任务只执行一次

                    }
                }
            } finally {
                // 回收多余线程 ，
                mainLock.lock();
                try {
                    if (workers.size() > coreSize) {
                        workers.remove(this);
                    }
                    if (runningCount.get() > 0) {
                        runningCount.getAndDecrement();
                    }
                } finally {
                    mainLock.unlock();
                }


            }
        }
    }


    private boolean addWorker(Runnable task) {
        Worker w = new Worker(task);
        w.start();
        workers.add(w);
        return true;
    }


    class TaskRejectedException extends RuntimeException {
        TaskRejectedException(String msg) {
            super(msg);
        }
    }

    public static void main(String[] args) {
        CustomerThreadPool threadPool = new CustomerThreadPool(4, 10, 3000l, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(10));

        for (int i = 0; i < 20; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}
