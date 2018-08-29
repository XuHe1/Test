### Test
--
Java 底层实现,高级特性学习,持续更新

#### 并发
* 线程基础
	* volatile
* 并发包
	* 原子类
	* 集合
		* CAS
		* AQS
	* 工具
		* CountDownLatch
		* CyclicBarrier
		* Semaphore
	* Lock
		* synchronized(方法，对象，类)
		* ReentrantLock
		* 自旋锁
		* 分段锁（ConcurrentHashMap）
	* 异步
		* Executor
		* FutureTask
		
#### IO
* [官方教程](https://docs.oracle.com/javase/7/docs/technotes/guides/io/index.html)
* [epoll](https://en.wikipedia.org/wiki/Epoll)

	```
	Its function is to monitor multiple file descriptors to see whether I/O is possible on any of them.
	epoll used red-black tree (RB-tree) data structure to keep track of all file descriptors that are currently being monitored
	
	```

* [proactor]()
* [reactor]()

#### 源码阅读
--
阅读源码好处:

* 提高自己的代码质量（格式，语法等）
* 知其然知其所以然，精通一门语言，必须知道底层是如何实现的，如jdk concurrent集合源码
* 理解其中的`设计模式`， 如`spring aop`中的代理模式。
* 拓展自己的知识点，源码涉及的知识点比较广，具有扫盲作用，如`tomcat`源码，能学习底层 socket编程，能更好地理解tcp/http协议

todo:

* jdk 源码
* spring 源码
* tomcat8 源码 