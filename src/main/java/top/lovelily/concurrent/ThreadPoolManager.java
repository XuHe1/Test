package top.lovelily.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolManager {

	private static ThreadPoolManager tpm = new ThreadPoolManager();

	/**
	 * 线程池维护线程的核心数量。
	 * <br>当提交一个任务到线程池时，线程池会创建一个线程来执行任务，即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池核心大小时就不再创建。
	 * <br><b>注意：</b>一般来说，对于CPU密集型任务，此数量应该和CPU数目保持一致。
	 */
	private final static int CORE_POOL_SIZE = 10;

	/**
	 * 线程池允许创建的最大线程数。如果队列满了，并且已创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。值得注意的是如果使用了无界的任务队列这个参数就没什么效果。
	 * <br><b>注意：</b>此参数建议和线程池核心数量保持一直，否则会存在线程的执行顺序会不满足FIFO原则，在一些配合队列使用的特殊场景会出现问题。
	 */
	private final static int MAX_POOL_SIZE = 10;

	/** 线程池维护线程所允许的空闲时间 */
	private final static int KEEP_ALIVE_TIME = 10;

	/** 线程池所使用的缓冲队列大小 */
	public final static int WORK_QUEUE_SIZE = 5000;

	/** 线程池所使用的任务缓冲队列 */
	private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>(WORK_QUEUE_SIZE);

	/**
	 * 线程池饱和策略。当队列和线程池都满了，说明线程池处于饱和状态，那么采用此策略处理提交的新任务。
	 */
	final RejectedExecutionHandler myRejectedExecutionHandler = new RejectedExecutionHandler() {
		public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
			System.out.println("线程池出现被拒绝任务，已饱和！");
		}
	};

	/**
	 * 线程池
	 */
	final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
			CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
			taskQueue, this.myRejectedExecutionHandler);
	

	/** 将构造方法访问修饰符设为私有，禁止任意实例化。 */
	private ThreadPoolManager() {
	}

	/** 线程池单例创建方法 */
	public static ThreadPoolManager newInstance() {
		return tpm;
	}

	/** 向线程池中添加任务方法 */
	public void addExecuteTask(Runnable task) {
		if (task == null) {
			return;
		}
		threadPool.execute(task);
	}

	/** 表示线程池是否还有可用的处理能力 */
	public boolean hasCapable() {
		int taskQueueSize = taskQueue.size();
		if(taskQueueSize < WORK_QUEUE_SIZE){//表示有处理能力
			return true;
		}
		return false;
	}

	/** 得到线程池中的任务队列大小 */
	public int getTaskQueueSize() {
		return taskQueue.size();
	}
	
	/** 获取活动的线程数 */
	public int getActiveCount(){
		return threadPool.getActiveCount();
	}
	
	/** 线程池的当前线程数量。如果线程池不销毁的话，池里的线程不会自动销毁。 */
	public int getPoolSize(){
		return threadPool.getPoolSize();
	}
	
	/** 线程池曾经创建过的最大线程数量 */
	public int getLargestPoolSize(){
		return threadPool.getLargestPoolSize();
	}
	
	/** 线程池需要执行的任务数量 */
	public long getTaskCount(){
		return threadPool.getTaskCount();
	}
	
	/** 线程池在运行过程中已完成的任务数量。小于或等于taskCount */
	public long getCompletedTaskCount(){
		return threadPool.getCompletedTaskCount();
	}
	
}
