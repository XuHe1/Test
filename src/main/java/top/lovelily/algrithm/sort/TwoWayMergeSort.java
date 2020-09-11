package top.lovelily.algrithm.sort;

import java.util.Arrays;

/**
 * Desc: 归并排序
 * Author: xuhe
 * Date: 2019/2/13 6:20 PM
 * Version: 1.0
 */
public class TwoWayMergeSort {
    /**
     * 将两个有序数组，返回一个合并成后的大的有序数组，如果两个小数组是降序的，那么合并后依然是降序；反之亦然
     * @param firstArray
     * @param secondArray
     * @param asc 表示这两个数组是升序数组还是降序数组
     */
    public static int[] merge(int[] firstArray,int[] secondArray,boolean asc) {
        //创建一个临时数组，其大小等于两个要合并的数组的大小
        int[] resultArray=new int[firstArray.length+secondArray.length];
        //创建三个变量，分别表示三个数组当前迭代到的下标的位置
        int firstArrayIndex = 0, secondArrayIndex = 0, resultArrayIndex = 0;

        // firstArray secondArray同时进行迭代，在都没有迭代完的情况下，继续，否则跳出
        while (firstArrayIndex < firstArray.length && secondArrayIndex < secondArray.length)  {
            if(asc){//升序
                if (firstArray[firstArrayIndex] < secondArray[secondArrayIndex]){
                    resultArray[resultArrayIndex++] = firstArray[firstArrayIndex++];
                }else{
                    resultArray[resultArrayIndex++] = secondArray[secondArrayIndex++];
                }
            }else{//降序
                if (firstArray[firstArrayIndex] > secondArray[secondArrayIndex]){
                    resultArray[resultArrayIndex++] = firstArray[firstArrayIndex++];
                }else{
                    resultArray[resultArrayIndex++] = secondArray[secondArrayIndex++];
                }
            }

        }

        // secondArray所有元素已经迭代结束,但是firstArray没有迭代完，将firstArray的剩余元素直接拷贝到resultArray的后面
        while (firstArrayIndex < firstArray.length){
            resultArray[resultArrayIndex++] = firstArray[firstArrayIndex++];
        }

        // secondArray所有元素已经迭代结束,但是firstArray没有迭代完,将secondArray的剩余元素直接拷贝到resultArray的后面
        while (secondArrayIndex < secondArray.length){
            resultArray[resultArrayIndex++] = secondArray[secondArrayIndex++];
        }
        return resultArray;
    }

    public static void main(String[] args) {
        //准备2个待合并升序数组进行合并
        int[] firstArray = {1,3,5,7};
        int[] secondArray = {3,4,6,7};
        System.out.println("待合并升序数组:\n"+(Arrays.toString(firstArray)+"\n" + Arrays.toString(secondArray)));
        int[] resultArray = merge(firstArray, secondArray,true);
        System.out.println("合并后:\n" + Arrays.toString(resultArray));
        System.out.println();
        //准备2个待合并降序数组进行合并
        firstArray = new int[]{9,6,3,0};
        secondArray =new int[] {10,5,3,-1};
        System.out.println("待合并降序数组:\n"+(Arrays.toString(firstArray)+"\n" + Arrays.toString(secondArray)));
        resultArray = merge(firstArray, secondArray,false);
        System.out.println("合并后:\n" + Arrays.toString(resultArray));
    }
};