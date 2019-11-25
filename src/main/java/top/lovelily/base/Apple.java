package top.lovelily.base;

import java.util.HashMap;
/**
 * Desc: Apple
 * Author: xuhe
 * Date: 2019/11/25 10:38 下午
 * Version: 1.0
 */

public class Apple {
    private String color;

    public Apple(String color) {
        this.color = color;
    }

    public boolean equals(Object obj) {
        if(obj==null) return false;
        if (!(obj instanceof Apple))
            return false;
        if (obj == this)
            return true;
        return this.color.equals(((Apple) obj).color);
    }

    public static void main(String[] args) {
        Apple a1 = new Apple("green");
        Apple a2 = new Apple("red");

        //hashMap stores apple type and its quantity
        // 1. location array index by hashcode
        // 2. key (== 或 equals)覆盖， 否则添加到链表尾部
        HashMap<Apple, Integer> m = new HashMap<Apple, Integer>();
        m.put(a1, 10);
        m.put(a2, 20);

        //  1. location array index by hashcode(object hashcode usually is the address), failed
        // return null
        Integer count = m.get(new Apple("green"));
        System.out.println(count);
    }
}