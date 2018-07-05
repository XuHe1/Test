package com.etianxia;

/**
 * Created by xuhe on 2018/5/12.
 */
public class TestException {
    public static void main(String[] args) {
        try {
            int a = 10/0;
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("hello");

    }
}
