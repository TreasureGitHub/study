package com.ffl.study.java.sort;

import com.ffl.study.common.utils.ArrayUtils;

/**
 * @author lff
 * @datetime 2020/06/10 23:34
 * 冒泡排序
 */
public class BubbleSort{


    public static void sort(int[] arr) {

        // 是否有进行过交换，默认为false，如果没有进行过交换，则表示已经有序了
        boolean flg = false;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    ArrayUtils.swap(arr, j, j + 1);
                    flg = true;
                }
            }
            // System.out.println("第" + (i + 1) + "次排序后的结构");
            // System.out.println(Arrays.toString(arr));

            if (!flg) {
                break;
            } else {
                flg = false;
            }
        }
    }
}
