package com.ffl.study.java.structure.recursion;

import com.ffl.study.common.utils.ArrayUtils;

/**
 * @author lff
 * @datetime 2020/06/13 22:17
 * <p>
 * 迷宫问题：
 *
 * 使用递归回溯
 */
public class Maze {

    public static void main(String[] args) {
        int[][] map = new int[8][7];

        // 1 表示墙，
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }

        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        map[3][1] = 1;
        map[3][2] = 1;

        // map[1][2] = 1;
        // map[2][2] = 1;

        ArrayUtils.printTwoDim(map);

        System.out.println("------------------------------------");
        setWay(map,1,1);
        ArrayUtils.printTwoDim(map);
    }


    /**
     * 约定：
     * 1.map中四面都是墙，用1表示挡板(不能走的地方)
     * 2.i,j 表示从地图的哪个位置开始找(1,1)
     * 3.如果小球能到map[6][5] 位置，说明通路找到
     * 4.当map[i][j] 为0，表示该点没有走过，当为1表示墙，2表示通路可以走；3表示该点已经走过，但是走不通
     * 5.走迷宫时，需要定一个策略 下 -> 右 -> 上 -> 左。如果该点走不通，再回溯
     *
     * @param map 地图
     * @param i   从哪个位置开始找
     * @param j
     * @return 如果找到，就返回true，否则返回false
     */
    private static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        } else {
            // 如果没有走过
            if (map[i][j] == 0) {
                // 假定该点可以走通
                // System.out.println("i = " + i + " j = " +j);
                map[i][j] = 2;
                if (setWay(map, i + 1, j)) {
                    return true;
                } else if (setWay(map, i, j + 1)) {
                    return true;
                } else if (setWay(map, i - 1, j)) {
                    return true;
                } else if (setWay(map, i - 1, j)) {
                    return true;
                } else {
                    // 说明该点走不通，重新置为3，死路
                    System.out.println("i = " + i + " j = " +j);
                    map[i][j] = 3;
                    return false;
                }
            } else {
                // 如果map[i][j] != 0，可能是1、2、3
                return false;
            }
        }
    }
}
