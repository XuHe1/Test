package top.lovelily.algrithm.sort;

import java.util.Arrays;

/**
 * Desc: TestHeapSort
 *
 * 1. 构建堆：从右到左，从下到上，从第一个非叶子节点起，与孩子节点比较后调整为大顶堆（或小顶堆）， 如果孩子节点又有孩子，递归调整下去
 * 3. 排序：将root节点与第n节点交换，调整前 n-1个节点为大顶堆（或小顶堆）
 *    4                   4                     9
 *   /\                  /\                    /\
 *  6  8    ..6..       9 8     ..4           6 8
 * /\                  /\                    /\
 *5 9                 5 6                   5 4
 * Author: xuhe
 * Date: 2020/8/26 4:25 下午
 * Version: 1.0
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = new int[]{4,6,8,5,9};
        int length = arr.length;
        //从最后一个非叶节点开始构建大顶堆
        for (int i = arr.length/2-1; i >=0; i--) {
            maximumHeap(i,arr,length);
        }
        //从最小的叶子节点开始与根节点进行交换并重新构建大顶堆
        for (int i = arr.length-1; i >=0; i--) {
//            System.out.println(Arrays.toString(arr));
            swap(arr,0,i);
            length--;
            maximumHeap(0,arr,length);
        }
        System.out.println(Arrays.toString(arr));
    }
    //构建大顶堆
    public static void maximumHeap(int i,int[] arr,int length){
        int temp = arr[i];
        for (int j = i*2+1; j < length; j=j*2+1) {
            //如果右孩子大于左孩子，则指向右孩子
            if(j+1<length && arr[j+1]>arr[j]){
                j++;
            }
            //如果最大的孩子大于当前节点，则将大孩子赋给当前节点，修改当前节点为其大孩子节点，再向下走。
            if(arr[j]>temp){
                arr[i] = arr[j];
                i = j;
            }else{
                break;
            }
        }
        //将temp放到最终位置
        arr[i] = temp;
    }
    //交换
    public static void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
