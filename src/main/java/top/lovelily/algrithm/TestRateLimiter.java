package top.lovelily.algrithm;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Desc: RateLimiter 基于 令牌桶 算法实现的限流方式
 * Author: Xu He
 * created: 2022/3/18 14:49
 */

public class TestRateLimiter {
    public static void main(String[] args) {
        // 每秒处理 2 个请求
        RateLimiter rateLimiter = RateLimiter.create(2);
        for (int i = 0; i < 10; i++) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
            System.out.println(time + ":" + rateLimiter.tryAcquire());
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
