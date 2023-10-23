package top.lovelily.algrithm.sort;

import java.util.Arrays;

/**
 * Desc: BubbleSort
 * Author: xuhe
 * Date: 2019/2/13 6:17 PM
 * Version: 1.0
 */
public class BubbleSort {
    /**
     * @param arr 需要排序的数组
     * @param asc 是否升序
     * @return 排序后的数组
     */
    public static void sort(int[] arr,boolean asc){
        // n-1 趟
        for (int i = 0; i < arr.length; i++) {
            // 相邻元素两两比较
            for (int j=0;j<arr.length-1;j++){
                if(asc){
                    if(arr[j]>arr[j+1]){//升序
                        swap(arr, j, j+1);
                    }
                }else{
                    if(arr[j]<arr[j+1]){//降序
                        swap(arr, j, j+1);
                    }
                }
            }
        }
    }

    //交换数组中两个元素的位置
    private static void swap(int[] arr, int i, int j) {
        int temp= arr[i];
        arr[i] =arr[j];
        arr[j]=temp;
    }

    public static void main(String[] args) {
        int[] arr=new int[]{1,5,6,8,9,4,3};
        System.out.println("排序数组："+Arrays.toString(arr));
        sort(arr,true);
        System.out.println("升序排列："+Arrays.toString(arr));
        sort(arr,false);
        System.out.println("降序排列："+Arrays.toString(arr));
    }
}