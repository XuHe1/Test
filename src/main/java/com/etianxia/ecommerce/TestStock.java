package com.etianxia.ecommerce;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

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
            Connection connection = DriverManager.getConnection("jdbc:mysql://mysql.test.getqood.com/kyx_ota?useUnicode=true&characterEncoding=UTF-8", "sysops", "fmicM;3+Gq[vcnfJ");
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
     * todo: 秒杀
     * 1. 用户请求放入redis队列
     * 2. 商品放入队列
     */
    @Test
    public void testSpike() {
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

}
