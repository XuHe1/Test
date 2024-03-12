package top.lovelily.algrithm.sort;

public class TestBitMap {
    public static void main(String[] args) {
        int arry[]  = {8,1,3, 4, 7,2,9};
        // 构建bitmap
        // 1. 确定bitmap长度，数组中的最大值
        int max = arry[0];
        for (int i = 0;i< arry.length;i++) {
            if (arry[i] > max) {
                max = arry[i];
            }
        }
        // 2.构建bitmap
        byte[] bitmap = new byte[max+1];
        for (int i = 0; i < arry.length; i++) {
            bitmap[arry[i]] = 1;
        }
        // 倒序输出bitmap
        for (int i = bitmap.length - 1; i >=0; i--) {
            if (bitmap[i] ==1) {
                System.out.println(i);
            }

        }

    }
}