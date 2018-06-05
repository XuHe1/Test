package com.etianxia.designpattern;

import com.etianxia.User;

/**
 * @author h.xu
 * @create 2017-11-17 下午5:32
 **/

public enum  TestEnumClass {
    SP;  // enum 创建单例
    private  static User xuhe = new User(1, "xuhe");
    private  User user;
    public void start() {
        user = new User(2, "zhangsan");
    }

    public static void main(String[] args) {
        TestEnumClass.SP.start();
        System.out.println(TestEnumClass.SP.xuhe.getId());
        System.out.println(TestEnumClass.SP.user.getId());
    }
}
