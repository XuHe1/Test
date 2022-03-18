package top.lovelily.algrithm.sort;

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
        //直到start>=end时结束递归
        if(start<end){
            int left = start;
            int right = end;
            // 基数定义为第一个元素
            int temp = arr[start];

            while(left<right){

                //右面的数字大于标准数时，右边的数的位置不变，right指针向左移一个位置
                while(left<right && arr[right]>temp){
                    right--;
                }


                //右边的数字及下标小于或等于基本数，将右边的数放到左边，left指针右移一个位置
                if(left<right) {
                    arr[left] = arr[right];
                    left++;
                }

                ////左边的数字小于或等于标准数时，左边的数的位置不变，指针向右移一个位置
                while(left<right && arr[left]<=temp){
                    left++;
                }

                //左边的数字大于基本数，将左边的数放到右边
                arr[right] = arr[left];

                System.out.println(Arrays.toString(arr));
            }

            //一趟循环结束，此时left=right，将基数放到这个重合的位置，
            arr[left] = temp;

            //将数组从left位置分为两半，继续递归下去进行排序
            recSort(arr,start,left);
            recSort(arr,left+1,end);
        }
    }

    public static void main(String[] args) {
        int[] arr={3,1,9,0,4,7,2,6,5,8};
        System.out.println("排序前数组:"+ Arrays.toString(arr));
        sort(arr);
        System.out.println("排序后数组:"+ Arrays.toString(arr));
    }

    void sort1(int[] arrays) {
        int temp = arrays[0];
        int start = 0;
        int end = arrays.length - 1;
        int left = start;
        int right = end;
        while (right > left & arrays[right] > temp ) {
            right --;
        }
        if (left < right) {
            arrays[left] = arrays[right];
            left ++;
        }

        while (arrays[left] < temp && left < right) {
            left ++;
        }

        arrays[left] = arrays[right];

    }
}
