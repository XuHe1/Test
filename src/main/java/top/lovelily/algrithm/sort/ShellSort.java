package top.lovelily.algrithm.sort;

import java.util.Arrays;

/**
 * Desc: ShellSort
 * Author: xuhe
 * Date: 2020/8/26 4:28 下午
 * Version: 1.0
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] arr = new int[]{10,6,3,8,33,27,66,9,7,88};
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    private static void shellSort(int[] arr) {
        int temp;
        //控制增量序列,增量序列为1的时候为最后一趟
        for (int i = arr.length/2; i >0; i/=2) {
            //根据增量序列，找到每组比较序列的最后一个数的位置
            for (int j = i; j < arr.length; j++) {
                //根据该比较序列的最后一个数的位置，依次向前执行插入排序
                for (int k = j-i; k >=0; k-=i) {
                    if(arr[k]>arr[k+i]){
                        temp = arr[k];
                        arr[k]  = arr[k+i];
                        arr[k+i] = temp;
                    }
                }
            }
        }
    }
}
