package com.etianxia;

/**
 *
 * 终止方法：
 *      1. return
 *      2. throw new Exception
 *
 * Created by xuhe on 2018/5/12.
 */
public class TestException {
    public static void main(String[] args) throws Exception {
        try {
            int a = 10/0;
        } catch (Exception e) {
            System.out.println(e);
            return; // catch 后程序会继续往下走，所以如果是致命性异常，需要return， 或者手动抛出去
        }
        System.out.println("hello");

    }
}
