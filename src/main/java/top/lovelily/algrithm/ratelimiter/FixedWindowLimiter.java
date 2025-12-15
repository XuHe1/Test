package top.lovelily.algrithm.ratelimiter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://juejin.cn/post/6870396751178629127
 * 固定窗口，
 * 窗口指的是时间窗口，就是一段时间，比如1s
 * 固定的时间大小范围内统计请求数量，超过最大数量限流
 *
 * 优点：实现简单，容易理解。
 * 缺点：流量曲线可能不够平滑，有“突刺现象”，
 * 1. 时间窗口内，某一段时间可能服务不可用，比如1s, 前100ms 处理了所有请求，后900ms 服务不可用
 * 2. 窗口切换时，可能产生2倍最大流量，比如第一个窗口后500ms,第二个窗口前500ms,在一起刚好也是1s，这两个500ms 恰巧处理了各自窗口的所有请求
 *
 **/

public class FixedWindowLimiter {

    private int windowSize; //窗口大小，毫秒为单位
    private int limit;//窗口内限流大小
    private AtomicInteger count;//当前窗口的计数器

    private FixedWindowLimiter(){}

    public FixedWindowLimiter(int windowSize, int limit){
        this.limit = limit;
        this.windowSize = windowSize;
        count = new AtomicInteger(0);

        //开启一个线程，达到窗口结束时清空count
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    count.set(0);
                    try {
                        Thread.sleep(windowSize);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //请求到达后先调用本方法，若返回true，则请求通过，否则限流
    public boolean tryAcquire(){
        int newCount = count.addAndGet(1);
        if(newCount > limit){
            return false;
        }else{
            return true;
        }
    }

    //测试
    public static void main(String[] args) throws InterruptedException {
        //每秒20个请求
        FixedWindowLimiter counterLimiter = new FixedWindowLimiter(1000,20);
        int count = 0;
        //模拟50次请求，看多少能通过
        for(int i = 0;i < 50;i ++){
            if(counterLimiter.tryAcquire()){
                count ++;
            }
        }
        System.out.println("第一拨50次请求中通过：" + count + ",限流：" + (50 - count));
        //过一秒再请求
        Thread.sleep(1000);
        //模拟50次请求，看多少能通过
        count = 0;
        for(int i = 0;i < 50;i ++){
            if(counterLimiter.tryAcquire()){
                count ++;
            }
        }
        System.out.println("第二拨50次请求中通过：" + count + ",限流：" + (50 - count));
    }

}

