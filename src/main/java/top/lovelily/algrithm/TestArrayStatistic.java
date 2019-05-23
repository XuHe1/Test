package top.lovelily.algrithm;

import java.util.HashMap;

/**
 * Desc: 统计一个素组中，每个元素出现的次数
 * Author: xuhe
 * Date: 2019/2/25 6:21 PM
 * Version: 1.0
 */
public class TestArrayStatistic {
    public static void main(String[] args) {
        int a[] = {1,2,3,1,3,2,4,7,8,9,0,2};
        count(a);
    }

    private static void count(int[] array) {
        HashMap<Integer, Integer> dataMap = new HashMap<>();
        for (int data: array) {
            int key = new Integer(data);
            Integer count = dataMap.get(key);
            dataMap.put(key, count == null? 1: count + 1);
        }

        for (Integer key: dataMap.keySet()) {
            System.out.println(key + ": " + dataMap.get(key));
        }
    }
}
