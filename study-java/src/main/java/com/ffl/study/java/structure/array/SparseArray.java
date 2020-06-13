package com.ffl.study.java.structure.array;

import com.ffl.study.common.utils.ArrayUtils;

/**
 * @author lff
 * @datetime 2020/06/12 01:53
 * <p>
 * 稀疏数组
 */
public class SparseArray {

    public static void main(String[] args) {
        // 创建原始二位数组
        // 0表示没有棋子；1 黑子；2：蓝子
        int chessArr[][] = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        chessArr[9][7] = 2;

        // 原始数据
        ArrayUtils.printTwoDim(chessArr);

        int sum = 0;

        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr[i].length; j++) {
                if (chessArr[i][j] != 0) {
                    sum++;
                }
            }
        }

        // 生成稀疏数组
        int[][] sparseArr = new int[sum + 1][3];

        sparseArr[0][0] = chessArr.length;
        sparseArr[0][1] = chessArr[0].length;
        sparseArr[0][2] = sum;

        // 给稀疏数组进行赋值
        int start = 1;
        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr[0].length; j++) {
                if (chessArr[i][j] != 0) {
                    sparseArr[start][0] = i;
                    sparseArr[start][1] = j;
                    sparseArr[start][2] = chessArr[i][j];
                    start++;
                }
            }
        }

        System.out.println("------得到稀疏数组------");
        ArrayUtils.printTwoDim(sparseArr);

        // 将稀疏数组进行恢复
        int[][] resArr = new int[sparseArr[0][0]][sparseArr[0][1]];
        for (int i = 1; i < sparseArr.length; i++) {
                resArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        // 将稀疏数组进行恢复
        System.out.println("------恢复稀疏数组------");
        ArrayUtils.printTwoDim(resArr);
    }
}
