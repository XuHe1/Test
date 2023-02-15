package top.lovelily.concurrent;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Desc: TestStock
 * Author: xuhe
 * Date: 2018/9/26 上午10:20
 * Version: 1.0
 */
public class TestStock {
    private Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/kyx-product?useUnicode=true&characterEncoding=UTF-8", "root", "123456");
            connection.setAutoCommit(false);
            return connection;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  虚拟商品，唯一标识（券码）
     */
    @Test
    public void testCouponStock() {
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        String sql = "UPDATE coupon set state=1 where state=0 and id=1";
                        Connection connection = getConnection();
                        Statement statement = connection.createStatement();
                        int record = statement.executeUpdate(sql);
                        connection.commit();
                        connection.close();
                        System.out.println(record);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            });

            thread.start();
            latch.countDown();
        }

        try {
            Thread.sleep(20000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实体商品，有库存概念
     */
    @Test
    public void testProductStock() {
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        String sql = "UPDATE product set stock=stock-2 where id=1 and stock>0";
                        Connection connection = getConnection();
                        System.out.println(connection);
                        Statement statement = connection.createStatement();
                        int record = statement.executeUpdate(sql);
                        if (record > 0) {
                            System.out.println("下单成功");
                        } else {
                            System.out.println("库存不足");
                        }

                        connection.commit();
                        connection.close();


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            });

            thread.start();
            latch.countDown();
        }

        try {
            Thread.sleep(20000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分布式锁，程序串行执行，即使数据库不加锁，仍不会超卖
     * 实体商品，有库存概念
     */
    @Test
    public void updateStockWithDL() throws InterruptedException {
        Config config = new Config();
       // config.setTransportMode(TransportMode.KQUEUE);
        config.useSingleServer().setAddress("redis://redis.test.getqood.com:6379");
                // use "rediss://" for SSL connection
              //  .addNodeAddress("redis://redis.test.getqood.com:6379");


        RedissonClient redissonClient = Redisson.create(config);
        RLock lock = redissonClient.getLock("Stock-Lock");
        // RedLock 多实例，更安全，不会像主从一样，会有多个线程同时获取锁，多个 master 互相独立的。
        //RedissonRedLock

        config.useSingleServer().setAddress("redis://redis.test.getqood.com:6380");
        RedissonClient redissonClient2 = Redisson.create(config);
        RLock lock2 = redissonClient.getLock("Stock-Lock");

        // <a href="org.redisson.RedissonMultiLock.java"></a>

        RedissonRedLock redLock = new RedissonRedLock(lock, lock2);

        System.out.println(lock); // org.redisson.RedissonLock@1da2cb77
        if (lock.tryLock(3000l, 3000l, TimeUnit.MILLISECONDS)) {
            try {

                String sql = "select stock from product";
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                int stock = 0;
                if (resultSet.next()) {
                   stock = resultSet.getInt("stock");
                }
                if (stock == 0) {
                    System.out.println("库存不够");
                    resultSet.close();
                    connection.close();
                    return; // finally 会执行
                }
                sql = "UPDATE product set stock=stock-1 where id=1 "; // and stock>0
                int record = statement.executeUpdate(sql);
                if (record > 0) {
                    System.out.println("下单成功");
                } else {
                    System.out.println("库存不足");
                }
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        } else {
            System.out.println("系统繁忙");
        }

    }


    @Test
    public void testProductStockWithDL() {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        updateStockWithDL();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            });

            thread.start();
            latch.countDown();
        }

        try {
            Thread.sleep(30000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * HMSET goodsId total 100 booked 0
     * "goodsId" : {
     *     "total": 100
     *     "booked": 100
     *  }
     *
     * src/redis-cli script load "$(cat spike.lua)"
     * local n = tonumber(ARGV[1])
     * if not n  or n == 0 then
     *     return 0
     * end
     * local vals = redis.call("HMGET", KEYS[1], "total", "booked");
     * local total = tonumber(vals[1])
     * local blocked = tonumber(vals[2])
     * if not total or not blocked then
     *     return 0
     * end
     * if blocked + n <= total then
     *     redis.call("HINCRBY", KEYS[1], "booked", n)
     *     return n;
     * end
     * return 0
     *
     *
     */



    /**
     * 执行lua脚本，返回秒杀数量，0失败，大于0成功
     * todo: lua
     */
    private void spike() {
        Jedis jedis = new Jedis("localhost", 6379);
        // EVALSHA 332db26ff2ac0cc85572ba12a42c4937e347a9ec 1 goodsId 1
        String script = "332db26ff2ac0cc85572ba12a42c4937e347a9ec";
        long result = (long) jedis.evalsha(script, 1, "goodsId", "1");

        if (result == 0) {
            System.out.println("秒杀失败，下次再来");
            return;
        }
        System.out.println("秒杀成功");

    }


    @Test
    public void cacheStock() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("start", "1");
        jedis.set("total", "10");
        jedis.set("booked", "0");
    }

    /**
     * redis操作是原子的，但是程序非原子
     * @param userId
     */
    private void spike(int userId) {
        Jedis jedis = new Jedis("localhost", 6379);
        int isStart = Integer.parseInt(jedis.get("start"));
        if (isStart == 0) {
            System.out.println("活动未开始");
            return;
        }
        long booked = Integer.parseInt(jedis.get("booked"));
        int total = Integer.parseInt(jedis.get("total")); //
        if (booked < total) { // 多个线程同时行执行到此，都拿到了相同的total
            jedis.incr("booked"); // 出现超卖现象
            System.out.println("User-" + userId + "秒杀成功");
            return;
        }
        System.out.println("User-" + userId +"秒杀失败，下次再来");


    }


    /**
     *
     * https://help.aliyun.com/document_detail/63920.html?spm=a2c4g.11186623.6.628.2b56b553o5uKgN
     * 1. 用户请求放入redis队列
     * 2. 商品放入队列
     */
    @Test
    public void testSpike() {

        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 200; i++) {
            int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        // spike(finalI);
                        spike();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });

            thread.start();
            latch.countDown();
        }

        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
