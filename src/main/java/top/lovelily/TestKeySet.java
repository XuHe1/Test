package top.lovelily;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Desc: TestKeySet
 * Author: Xu He
 * created: 2021/11/29 17:37
 */

public class TestKeySet {
    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        System.out.println(map.keySet().size());

    }
}
