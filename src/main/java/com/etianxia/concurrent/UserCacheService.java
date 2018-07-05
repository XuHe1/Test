package com.etianxia.concurrent;

import com.etianxia.User;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author h.xu
 * @create 2018-01-25 上午10:17
 **/

public class UserCacheService {

    public static ConcurrentHashMap<String, User>  cache = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Object> redisCache = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String,FutureTask<User>> userMap = new ConcurrentHashMap<>();

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    // 会被击穿， 压力落在数据库上
    public User getUser(String key) {
        User user = cache.get(key);
        if (user != null) {
            System.out.println("================get from cache success!==================");
            return user;
        }

        System.out.println("================query from db success!==================");
        try {
            Thread.sleep(200l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user = new User(1, "xuhe", 20, 175, "Shanghai, China.");
        cache.put(user.getName(), user);
        return user;

    }

    // synchronized 方法 不会被击穿，但性能受影响
    public synchronized User getUser1(String key) {
        User user = cache.get(key);
        if (user != null) {
            System.out.println("================get from cache success!==================");
            return user;
        }

        System.out.println("================query from db success!==================");
        try {
            Thread.sleep(200l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user = new User(1, "xuhe", 20, 175, "Shanghai, China.");
        cache.put(user.getName(), user);
        return user;

    }

    // synchronized + double check 不会 击穿
    public User getUser21(String key) {
        User user = cache.get(key);
        if (user != null) {
            System.out.println("================get from cache success!==================");
            return user;
        }

        synchronized(this) {
            user = cache.get(key);
            if (user != null) {
                System.out.println("================get from cache success!==================");
                return user;
            }
            try {
                Thread.sleep(200l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("================query from db success!==================");
            user = new User(1, "xuhe", 20, 175, "Shanghai, China.");
            cache.put(user.getName(), user);
        }

        return user;

    }

    // synchronized 无 double check 还是会击穿
    public User getUser22(String key) {
        User user = cache.get(key);
        if (user != null) {
            System.out.println("================get from cache success!==================");
            return user;
        }

        /**
         *  --------------  高并发情况下，线程都执行到此（缓存读取失败）， 下面代码无 double check，还是会读数据库
         */

        synchronized(this) {

            try {
                Thread.sleep(200l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("================query from db success!==================");
            user = new User(1, "xuhe", 20, 175, "Shanghai, China.");
            cache.put(user.getName(), user);
        }

        return user;

    }


    // 还是会击穿
    public  User getUser3(String key) {
        readWriteLock.readLock().lock();
        User user = cache.get(key);
        if (user != null) {
            System.out.println("================get from cache success!==================");
            readWriteLock.readLock().unlock();
            return user;
        }

        readWriteLock.readLock().unlock();


        /**
         *  --------------  高并发情况下，线程都执行到此（缓存读取失败）， 下面代码如果无 double check，还是会读数据库
         */

        readWriteLock.writeLock().lock();

//        user = cache.get(key);
//        if (user != null) {
//            System.out.println("================get from cache success!==================");
//            readWriteLock.writeLock().unlock(); // 保证在各种条件下，锁都被释放
//            return user;
//        }

        try {
            Thread.sleep(200l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user = new User(1, "xuhe", 20, 175, "Shanghai, China.");
        cache.put(user.getName(), user);
        System.out.println("================query from db success!==================");
        readWriteLock.writeLock().unlock();

        return user;

    }

    public  User getUser41(String key) {

        User user;
        readLock.lock();
        user = cache.get(key);
        if (user != null) {
            System.out.println("================get from cache success!==================");
            readLock.unlock();
            return user;
        } else {
            readLock.unlock();

            writeLock.lock();
            // Double check
            user = cache.get(key);
            if (user != null) {
                System.out.println("================get from cache success!==================");
                writeLock.unlock();
                return user;
            }
            user = new User(1, "xuhe", 20, 175, "Shanghai, China.");
            System.out.println("================query from db success!==================");
            cache.put(user.getName(), user);
            writeLock.unlock();

        }

        return user;

    }

    // ReadWriteLock + double check 不会被击穿，性能较好
    public  User getUser4(String key) {

        User user;
        readLock.lock();

        try {
            user = cache.get(key);
            if (user != null) {
                System.out.println("================get from cache success!==================");
            } else {
                readLock.unlock();

                writeLock.lock();
                try {
                    // Double check
                    user = cache.get(key);
                    if (user != null) {
                        System.out.println("================get from cache success!==================");
                        return user;
                    }
                    user = new User(1, "xuhe", 20, 175, "Shanghai, China.");
                    System.out.println("================query from db success!==================");
                    cache.put(user.getName(), user);
                } finally {
                    writeLock.unlock();
                    readLock.lock();
                }
            }

        } finally {
            readLock.unlock();  // 都会执行
        }

        return user;

    }

    // Future 只计算一次,不可重复计算,get方法block,直到计算完成
    public User getUserAsync(String key) throws ExecutionException, InterruptedException {
        FutureTask<User>connectionTask=userMap.get(key);
        if(connectionTask!=null){
            System.out.println(Thread.currentThread().getName() + ": Get from Cache^V^");
            return connectionTask.get();
        }
        else{
            Callable<User> callable = new Callable<User>(){
                @Override
                public User call() throws Exception {
                    User user = new User(1, "xuhe");
                    System.out.println(Thread.currentThread().getName() + ": Query from DB=>" + user);
                    return user;
                }
            };
            FutureTask<User>newTask = new FutureTask<User>(callable);
            // return previous value, or null
            connectionTask = userMap.putIfAbsent(key, newTask);
            if(connectionTask==null){
                connectionTask = newTask;
                connectionTask.run();
            } else {
                System.out.println(Thread.currentThread().getName() + ": Get from Cache^V^");
            }
            return connectionTask.get();
        }

    }

    private  <T> T getFromCache(String key,  CacheLoadable<T> cacheLoadable) {

        T result;
        readLock.lock();

        try {
            result = (T) redisCache.get(key);
            if (result != null) {
                System.out.println("================get from cache success!==================");
            } else {
                readLock.unlock();

                writeLock.lock();
                try {
                    // Double check
                    result = (T) redisCache.get(key);
                    if (result != null) {
                        System.out.println("================get from cache success!==================");
                        return result;
                    }
                    result = cacheLoadable.load();
                    System.out.println("================query from db success!==================");
                    redisCache.put(key, result);
                } finally {
                    writeLock.unlock();
                    readLock.lock();
                }
            }

        } finally {
            readLock.unlock();  // 都会执行
        }

        return result;
    }

    public User getCacheUser(String key) {
        return getFromCache(key, new CacheLoadable<User>() {
            @Override
            public User load() {
               return new User(1, "xuhe", 20, 175, "Shanghai, China.");
            }
        });

    }

}
