package top.lovelily.algrithm;

import java.util.Arrays;

/**
 * Desc: QuickSort
 * Author: xuhe
 * Date: 2019/2/13 6:15 PM
 * Version: 1.0
 */
public class QuickSort {
    public static void sort(int[] arr){
        recSort(arr,0,arr.length-1);
    }
    public static void recSort(int[] arr,int start, int end){
        if(start>=end){
            return;
        }
        int partitionIndex = partition(arr, start, end);
        recSort(arr,start,partitionIndex);
        recSort(arr,partitionIndex+1,end);
    }
    /**
     * 返回划分后，左指针和右指针相遇的下标
     */
    public static int partition(int[] arr,int start,int end){
        int pivot=getPivot(arr,start,end);
        int left_pointer=start-1;
        int right_pointer=end+1;
        while (true){
            while (arr[++left_pointer]<pivot);//left_pointer当遇到比基准值大的元素，停下来
            while (arr[--right_pointer]>pivot);//right_pointer当遇到比基准值小的元素，停下来
            if(left_pointer>=right_pointer){break;}
            swap(arr,left_pointer,right_pointer);
        }
        return right_pointer;
    }

    /**
     * 获得基准值
     */
    private static int getPivot(int[] arr, int start, int end) {
        return arr[start];
    }

    //交换数组中两个元素的位置
    private static void swap(int[] arr, int i, int j) {
        int temp= arr[i];
        arr[i] =arr[j];
        arr[j]=temp;
    }

    public static void main(String[] args) {
        int[] arr={3,1,9,0,4,7,2,6,5,8};
        System.out.println("排序前数组:"+ Arrays.toString(arr));
        sort(arr);
        System.out.println("排序前数后:"+ Arrays.toString(arr));
    }
}
