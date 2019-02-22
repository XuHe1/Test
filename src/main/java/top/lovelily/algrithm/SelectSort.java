package top.lovelily.algrithm;

import java.util.Arrays;

/**
 * Desc: SelectSort
 * Author: xuhe
 * Date: 2019/2/13 6:18 PM
 * Version: 1.0
 */
public class SelectSort {
    public static void sort(int[] arr,boolean asc){
        for (int i = 0; i < arr.length; i++) {
            int index=i;
            for (int j = i+1; j < arr.length; j++) {
                if(asc){//升序，选择无序区最小的元素
                    if(arr[index]>arr[j]){
                        index=j;
                    }
                }else{//降序，选择无序区最大的元素
                    if(arr[index]<arr[j]){
                        index=j;
                    }
                }
            }

            if(index!=i){
                swap(arr,index,i);
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
        System.out.println("排序数组："+ Arrays.toString(arr));
        sort(arr,true);
        System.out.println("升序排列："+Arrays.toString(arr));
        sort(arr,false);
        System.out.println("降序排列："+Arrays.toString(arr));
    }
}