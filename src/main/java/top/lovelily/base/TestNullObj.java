package top.lovelily.base;

import java.util.Date;

public class TestNullObj {
    public static void main(String[] args) {
        Object t = null;
        System.out.println(t instanceof Date);// null 对象不属于任何对象的实例

        String s = null;
        System.out.println(s instanceof String); // false
    }
}
