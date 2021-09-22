package top.lovelily.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Desc: DateTimeUtils
 * Author: xuhe
 * Date: 2020/11/22 下午10:34
 * Version: 1.0
 */
public class DateTimeUtils {

    private static CountDownLatch latch = new CountDownLatch(10);

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("");

    private static String getString(Date date) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String str = localDateTime.format(dateTimeFormatter);
        return dateFormat.format(date);
    }

    private static Date parseFromStr(String str) {
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Date parseFromStrWithSafe(String str) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormatThreadLocal.set(simpleDateFormat);
            SimpleDateFormat simpleDateFormat1 = dateFormatThreadLocal.get();
            System.out.println(simpleDateFormat == simpleDateFormat1);

            Date date =  dateFormatThreadLocal.get().parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
            dateFormatThreadLocal.remove(); // 防止threadLocal内存溢出
        }
        return null;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   // System.out.println(getString(new Date()));
                  //  System.out.println(getString(new Date(1606057806000l + 1000l * finalI))); // 发现有重复的时间，说明线程不安全，
                    System.out.println(parseFromStrWithSafe("2020-11-22 22:47:03"));
                }
            });
            thread.start();
            latch.countDown();
        }


    }
}
