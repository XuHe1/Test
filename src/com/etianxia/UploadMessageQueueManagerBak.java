package com.etianxia;



/**
 * Description:
 * <p/>
 * 上行消息管理器,上行指从终端到服务器的消息传递
 * 4>todo: 需要完善, 线程问题, 效率问题
 * 4>todo: 考虑改成线程池主动取数据处理
 * <p/>
 * Created by HenryFour on 14-6-24
 */
public enum UploadMessageQueueManagerBak implements Runnable {
    SP;

    /**
     * 创建并开始上传消息处理线程
     */
    public void start() {
        new Thread(this).start();
    }

    public void run() {
        ThreadPoolManager threadPoolManager = ThreadPoolManager.newInstance();


        while (true) {//无限循环,不断地获取数据,并放到线程池中去处理
            try{

                if (!threadPoolManager.hasCapable()) {//表示线程池没有处理能力
                    System.out.println("线程池忙,需要休眠后再处理。" );
                    do {
                        try {
                            System.out.println("TaskQueueSize=" + threadPoolManager.getTaskQueueSize());
                                Thread.sleep(2000); //这里暂停的时间将来根据经验来调整
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (!threadPoolManager.hasCapable());
                }


                //向线程池中添加线程任务
                threadPoolManager.addExecuteTask(new UploadMessageHandlerBak());
                System.out.println(Thread.currentThread().getName());
            } catch (Exception e) {
                System.out.println(String.format("Exception caught, ThreadId:%d, ThreadName:%s, exception:%s",
                        Thread.currentThread().getId(), Thread.currentThread().getName(), e.toString()));
            }
        }
    }
}
