package top.lovelily.algrithm.ratelimiter;

/**
 * 滑动窗口
 * 将一个大的时间窗口切分为多个小的时间窗口，然后随着时间的推移而滑动窗口
 * 其实就是对请求数进行了更细粒度的限流，窗口划分的越多，则限流越精准。
 *
 * 优点：不会出现固定窗口切换期间2倍阈值流量
 * 避免了漏斗算法的”饥饿问题“
 *
 */
public class SlideWindowLimiter {


    private int windowSize; //窗口大小，毫秒为单位
    private int limit;//窗口内限流大小
    private int splitNum;//切分小窗口的数目大小
    private int[] counters;//每个小窗口的计数数组
    private int index;//当前小窗口计数器的索引
    private long startTime;//窗口开始时间

    private SlideWindowLimiter(){}

    public SlideWindowLimiter(int windowSize, int limit, int splitNum){
        this.limit = limit;
        this.windowSize = windowSize;
        this.splitNum = splitNum;
        counters = new int[splitNum];
        index = 0;
        startTime = System.currentTimeMillis();
    }

    //请求到达后先调用本方法，若返回true，则请求通过，否则限流
    public synchronized boolean tryAcquire(){
        long curTime = System.currentTimeMillis();
        // 当前时间需要滑动的小窗口数量
        long windowsNum = Math.max(curTime - windowSize - startTime,0) / (windowSize / splitNum);
        slideWindow(windowsNum);//滑动窗口
        int count = 0;
        // 统计所有小窗口的请求数
        for(int i = 0;i < splitNum;i ++){
            count += counters[i];
        }
        // 限流
        if(count >= limit){
            return false;
        }else{
            // 当前窗口计数+1
            counters[index] ++;
            return true;
        }
    }

    private synchronized void slideWindow(long windowsNum){
        if(windowsNum == 0)
            return;
        long slideNum = Math.min(windowsNum,splitNum);
        for(int i = 0;i < slideNum;i ++){
            counters[i] = 0; // 丢掉过期窗口的计数
        }
        // 当前窗口指向
        index = (index + 1) % splitNum;
//        for(int i = 0;i < slideNum;i ++){
//            index = (index + 1) % splitNum;
//            counters[index] = 0;
//        }
        startTime = startTime + windowsNum * (windowSize / splitNum);//更新滑动窗口时间
    }

    //测试
    public static void main(String[] args) throws InterruptedException {
        //每秒20个请求，100ms 滚动
        int limit = 20;
        SlideWindowLimiter counterSildeWindowLimiter = new SlideWindowLimiter(1000,limit,10);
        int count = 0;

        Thread.sleep(3000);
        //计数器滑动窗口算法模拟100组间隔30ms的50次请求
        System.out.println("计数器滑动窗口算法测试开始");
        System.out.println("开始模拟100组间隔150ms的50次请求");
        int faliCount = 0;
        for(int j = 0;j < 100;j ++){
            count = 0;
            for(int i = 0;i < 50;i ++){
                if(counterSildeWindowLimiter.tryAcquire()){
                    count ++;
                }
            }
            Thread.sleep(150);
            //模拟50次请求，看多少能通过
            for(int i = 0;i < 50;i ++){
                if(counterSildeWindowLimiter.tryAcquire()){
                    count ++;
                }
            }
            if(count > limit){
                System.out.println("时间窗口内放过的请求超过阈值，放过的请求数" + count + ",限流：" + limit);
                faliCount ++;
            }
            Thread.sleep((int)(Math.random() * 100));
        }
        System.out.println("计数器滑动窗口算法测试结束，100组间隔150ms的50次请求模拟完成，限流失败组数：" + faliCount);
        System.out.println("===========================================================================================");


        //计数器固定窗口算法模拟100组间隔30ms的50次请求
        System.out.println("计数器固定窗口算法测试开始");
        //模拟100组间隔30ms的50次请求
        FixedWindowLimiter counterLimiter = new FixedWindowLimiter(1000,limit);
        System.out.println("开始模拟100组间隔150ms的50次请求");
        faliCount = 0;
        for(int j = 0;j < 100;j ++){
            count = 0;
            for(int i = 0;i < 50;i ++){
                if(counterLimiter.tryAcquire()){
                    count ++;
                }
            }
            Thread.sleep(150);
            //模拟50次请求，看多少能通过
            for(int i = 0;i < 50;i ++){
                if(counterLimiter.tryAcquire()){
                    count ++;
                }
            }
            if(count > limit){
                System.out.println("时间窗口内放过的请求超过阈值，放过的请求数" + count + ",限流：" + limit);
                faliCount ++;
            }
            Thread.sleep((int)(Math.random() * 100));
        }
        System.out.println("计数器滑动窗口算法测试结束，100组间隔150ms的50次请求模拟完成，限流失败组数：" + faliCount);
    }

}
