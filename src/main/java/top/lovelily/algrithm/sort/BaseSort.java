package top.lovelily.algrithm.sort;

import java.util.Arrays;

/**
 * Desc: BaseSort
 * Author: xuhe
 * Date: 2020/8/26 4:28 下午
 * Version: 1.0
 */
public class BaseSort {
    public static void main(String[] args) {
        int[] arr = new int[]{10,6,3,8,33,27,66,9,7,88};
        radixSort(arr);
    }

    private static void radixSort(int[] arr) {
        //求出待排数的最大数
        int maxLength=0;
        for (int i = 0; i < arr.length; i++) {
            if(maxLength<arr[i])
                maxLength = arr[i];
        }
        //根据最大数求最大长度
        maxLength = (maxLength+"").length();

        //用于暂存数据的数组
        int[][] temp = new int[10][arr.length];
        //用于记录temp数组中每个桶内存的数据的数量
        int[] counts = new int[10];
        //用于记录每个数的i位数
        int num = 0;
        //用于取的元素需要放的位置
        int index = 0;
        //根据最大长度决定排序的次数
        for (int i = 0,n=1; i < maxLength; i++,n*=10) {
            for (int j = 0; j < arr.length; j++) {
                num = arr[j]/n%10;
                temp[num][counts[num]] = arr[j];
                counts[num]++;
            }

            //从temp中取元素重新放到arr数组中
            for (int j = 0; j < counts.length; j++) {
                for (int j2 = 0; j2 < counts[j]; j2++) {
                    arr[index] = temp[j][j2];
                    index++;
                }
                counts[j]=0;
            }
            index=0;
        }
        System.out.println(Arrays.toString(arr));
    }
}
