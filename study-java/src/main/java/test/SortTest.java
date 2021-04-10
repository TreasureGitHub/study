package test;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2021/01/24 15:20
 */
public class SortTest {

    public static void main(String[] args) {
        int[] arr = {0, 10, 9, 8, 3, 5, 7,};
        System.out.println(Arrays.toString(arr));

        selectSort(arr);

        System.out.println(Arrays.toString(arr));
    }

    /**
     * 对输入数据进行排序
     *
     * @param arr
     */
    public static void sort(int[] arr) {
        if (arr == null) {
            return;
        }

        int tmp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    public static void selectSort(int[] arr) {
        if (arr == null) {
            return;
        }

        int minIndex;
        int minValue;

        for (int i = 0; i < arr.length - 1; i++) {
            minIndex = i;
            minValue = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < minValue) {
                    minIndex = j;
                    minValue = arr[j];
                }
            }

            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = minValue;
            }
        }
    }

    public static void insertSort(int[] arr) {
        //待插入index 和 value
        int insertIndex;
        int insertValue;

        for (int i = 1; i < arr.length; i++) {
            // 待插入数组和值
            insertIndex = i;
            insertValue = arr[i];

            for (int j = i; j > 0; j--) {
                // 如果发现比较值值比前一个值小，则将前一个值向后移
                if (insertValue < arr[j - 1]) {
                    arr[j] = arr[j - 1];
                    // 没有立即将 比较值 和 前一位交换，而是记录下位置
                    insertIndex = j - 1;
                } else {
                    break;
                }
            }

            // 如果待插入位置和i不相等，则进行赋值
            if (insertIndex != i) {
                arr[insertIndex] = insertValue;
            }
        }
    }
}
