package top.lovelily.concurrent;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
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
     * 执行lua脚本，返回秒杀数量，0失败，大于0成功
     * todo: lua
     */
    private void spike(int userId) {
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


    /**
     *
     * https://help.aliyun.com/document_detail/63920.html?spm=a2c4g.11186623.6.628.2b56b553o5uKgN
     * 1. 用户请求放入redis队列
     * 2. 商品放入队列
     */
    @Test
    public void testSpike() {

        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    spike(finalI);
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
