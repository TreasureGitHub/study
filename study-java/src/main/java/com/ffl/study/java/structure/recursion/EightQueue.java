package com.ffl.study.java.structure.recursion;

import com.ffl.study.common.constants.StringConstants;

/**
 * @author lff
 * @datetime 2020/12/04 07:47
 */
public class EightQueue {

    private int max = 8;

    private int[] array = new int[max];

    private static int cnt = 0;
    private static int judgeCount = 0;

    public static void main(String[] args) {
        EightQueue eightQueue = new EightQueue();
        eightQueue.check(0);
        System.out.println("一共有" + cnt + "种解法");
        System.out.println("一共递归" + judgeCount + "次");
    }

    /**
     * 编写方法，放置第n个皇后
     * 注意：check是每一次递归时，进入到check中都有  for (int i = 0; i < max; i++)，因此会有回溯
     *
     * @param n
     */
    private void check(int n) {
        if (n == max) { //n = 8 , 其实8个皇后就既然放好
            print();
            return;
        }

        // 依次放入8个皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            // 先将当前皇后n ，放到改行的第一列
            array[n] = i;
            // 判断当放置的第n个皇后到i列是否冲突
            if (judge(n)) { // 不冲突
                // 接着放 n + 1 个皇后
                check(n + 1);
            }

            // 如果冲突，就继续执行array[n] = i,即将第n个皇后，放置在本行 后移的一个位置
        }
    }

    /**
     * @param n 表示第n个皇后
     * @return
     */
    private boolean judge(int n) {
        judgeCount ++;
        for (int i = 0; i < n; i++) {
            // 1.array[i] == array[n] 判断第n个皇后和前面的n-1个皇后是否在同一列
            // 2.Math.abs(n -1) == Math.abs(array[n] = array[i]) 表示判断第n个皇后是否和第i个皇后是否在同一斜线
            //    Math.abs(n -1) == Math.abs(array[n] = array[i])  相当于  Math.abs(array[n] = array[i]) /  Math.abs(n -1) == 1 斜率等于1表示在同一斜线
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }

        return true;
    }


    /**
     * 打印数组
     */
    public void print() {
        cnt ++;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + StringConstants.SPACE);
        }
        System.out.println();
    }
}
