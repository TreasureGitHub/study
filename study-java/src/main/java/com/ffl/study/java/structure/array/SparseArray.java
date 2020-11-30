package com.ffl.study.java.structure.array;

import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.constants.StringConstants;
import com.ffl.study.common.utils.ArrayUtils;
import com.ffl.study.common.utils.PathUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/06/12 01:53
 * <p>
 * 稀疏数组
 */
public class SparseArray {

    public static void main(String[] args) {
        // 初始化数组
        int[][] arr = new int[12][12];
        arr[5][3] = 2;
        arr[7][8] = 10;


        ArrayUtils.println(arr);

        System.out.println("---------------------原始数组---------------------");

        String path = PathUtils.getResFilePath(PathConstants.JAVA_RES_ABS, "sparseArray.txt");

        // 保存
        save(arr, path);

        System.out.println();

        // 恢复并打印
        ArrayUtils.println(recover(path));
        System.out.println("---------------------恢复数组---------------------");

        int[][] sparseArray = getSparseArray(arr);

        // 得到 稀疏数组
        ArrayUtils.println(sparseArray);
        System.out.println("---------------------稀疏数组---------------------");

        // 通过稀疏数组得到原始数组
        ArrayUtils.println(getOrigArray(sparseArray));
        System.out.println("---------------------原始数组---------------------");
    }

    /**
     * 保存数组至指定文件
     *
     * @param arr  原始数据
     * @param path 保存至指定位置
     * @throws IOException
     */
    private static void save(int[][] arr, String path) {
        int[][] sparseArray = getSparseArray(arr);

        try (FileWriter fileWriter = new FileWriter(path)) {
            for (int i = 0; i < sparseArray.length; i++) {
                fileWriter.append(ArrayUtils.join(sparseArray[i]));

                if (i != sparseArray.length - 1) {
                    fileWriter.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从指定位置 恢复稀疏数组
     * 传入的数组需要符合稀疏数组
     *
     * @param path 从指定位置恢复数组
     * @return 稀疏数组
     */
    private static int[][] recover(String path) {
        int[][] sparseArr = null;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(path))) {
            String line;
            // 第一行内容
            line = bufferReader.readLine();
            int[] info = ArrayUtils.toIntArray(line.split(StringConstants.TAB));
            sparseArr = new int[info[0]][info[1]];

            while ((line = bufferReader.readLine()) != null) {
                info = ArrayUtils.toIntArray(line.split(StringConstants.TAB));
                sparseArr[info[0]][info[1]] = info[2];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sparseArr;
    }

    /**
     * 得到稀疏数组
     *
     * @param arr 原始数组
     * @return    稀疏数组
     */
    private static int[][] getSparseArray(int[][] arr) {
        int sum = 0;

        // 1.第一次遍历，得到有效数值总数
        for (int[] ints : arr) {
            for (int j = 0; j < arr[0].length; j++) {
                if (ints[j] != 0) {
                    sum++;
                }
            }
        }

        // 2.初始化新的数据组
        int[][] sparseArr = new int[sum + 1][3];
        // 第0行第一列表示 原始数组 行数
        sparseArr[0][0] = arr.length;
        // 第0行第二列表示 原始数组的 列数
        sparseArr[0][1] = arr[0].length;
        // 表示有效值总数
        sparseArr[0][2] = sum;

        int curr = 1;
        // 将有效数值填入数组中
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] != 0) {
                    sparseArr[curr][0] = i;
                    sparseArr[curr][1] = j;
                    sparseArr[curr][2] = arr[i][j];
                    curr++;
                }
            }
        }

        return sparseArr;
    }

    /**
     * 传入稀疏数组，得到原始数组
     *
     * @param   arr 稀疏数组
     * @return      原始数组
     */
    public static int[][] getOrigArray(int[][] arr) {
        // 创建原始数组
        int[][] origArr = new int[arr[0][0]][arr[0][1]];

        // 遍历填入数据
        for (int i = 1; i < arr.length; i++) {
            origArr[arr[i][0]][arr[i][1]] = arr[i][2];
        }

        return origArr;
    }
}