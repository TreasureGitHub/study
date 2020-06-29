package com.ffl.study.java.sort1;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/05/27 10:05
 * <p>
 * https://www.runoob.com/w3cnote/quick-sort.html
 * https://blog.csdn.net/nrsc272420199/article/details/82587933
 * <p>
 * 快速排序
 */
public class QuickSort1 {

    public static void main(String[] args) {
        // int arr[] = {-9, 78, 0, 23, -567, 70};
        int[] arr = {6, 1, 2, 6, 6, 6, 3, 8, 6, 6};
        sort(arr);
        System.out.println(Arrays.toString(arr));

    }

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }


    public static void quickSort(int[] arr,int left, int right) {
        int l = left; //×óÏÂ±ê
        int r = right; //ÓÒÏÂ±ê
        //pivot ÖÐÖáÖµ
        int pivot = arr[(left + right) / 2];
        int temp = 0; //ÁÙÊ±±äÁ¿£¬×÷Îª½»»»Ê±Ê¹ÓÃ
        //whileÑ­»·µÄÄ¿µÄÊÇÈÃ±Èpivot ÖµÐ¡·Åµ½×ó±ß
        //±Èpivot Öµ´ó·Åµ½ÓÒ±ß
        while( l < r) {
            //ÔÚpivotµÄ×ó±ßÒ»Ö±ÕÒ,ÕÒµ½´óÓÚµÈÓÚpivotÖµ,²ÅÍË³ö
            while( arr[l] < pivot) {
                l += 1;
            }
            //ÔÚpivotµÄÓÒ±ßÒ»Ö±ÕÒ,ÕÒµ½Ð¡ÓÚµÈÓÚpivotÖµ,²ÅÍË³ö
            while(arr[r] > pivot) {
                r -= 1;
            }
            //Èç¹ûl >= rËµÃ÷pivot µÄ×óÓÒÁ½µÄÖµ£¬ÒÑ¾­°´ÕÕ×ó±ßÈ«²¿ÊÇ
            //Ð¡ÓÚµÈÓÚpivotÖµ£¬ÓÒ±ßÈ«²¿ÊÇ´óÓÚµÈÓÚpivotÖµ
            if( l >= r) {
                break;
            }

            //½»»»
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            //Èç¹û½»»»Íêºó£¬·¢ÏÖÕâ¸öarr[l] == pivotÖµ ÏàµÈ r--£¬ Ç°ÒÆ
            if(arr[l] == pivot) {
                r -= 1;
            }
            //Èç¹û½»»»Íêºó£¬·¢ÏÖÕâ¸öarr[r] == pivotÖµ ÏàµÈ l++£¬ ºóÒÆ
            if(arr[r] == pivot) {
                l += 1;
            }
        }

        // // Èç¹û l == r, ±ØÐël++, r--, ·ñÔòÎª³öÏÖÕ»Òç³ö
        // if (l == r) {
        //     l += 1;
        //     r -= 1;
        // }
        // //Ïò×óµÝ¹é
        // if(left < r) {
        //     quickSort(arr, left, r);
        // }
        // //ÏòÓÒµÝ¹é
        // if(right > l) {
        //     quickSort(arr, l, right);
        // }


    }
}