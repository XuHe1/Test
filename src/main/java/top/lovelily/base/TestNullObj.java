package top.lovelily.base;

import java.util.Date;

public class TestNullObj {
    public static void main(String[] args) {
        Object t = null;
        System.out.println(t instanceof Date);// null 对象不属于任何对象的实例
    }
}
