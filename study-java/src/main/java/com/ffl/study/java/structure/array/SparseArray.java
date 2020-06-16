package com.ffl.study.java.structure.array;

import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.utils.ArrayUtils;
import com.ffl.study.common.utils.PathUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/06/12 01:53
 *
 * 稀疏数组
 */
public class SparseArray {

    public static void main(String[] args) throws IOException {

        int[][] arr = new int[11][11];

        arr[5][3] = 2;
        arr[7][8] = 10;

        ArrayUtils.println(arr);

        String path = PathUtils.getResourceFilePath(PathConstants.JAVA_RES_ABS,"sparseArray.txt");

        save(arr, path);

        ArrayUtils.println(recover(path));

        int[][] sparseArray = getSparseArray(arr);
        ArrayUtils.println(sparseArray);

        ArrayUtils.println(getOrigArray(sparseArray));
    }

    /**
     * 保存数组至指定文件
     *
     * @param arr       原始数据
     * @param path      保存至指定位置
     * @throws IOException
     */
    public static void save(int[][] arr, String path) throws IOException {
        int[][] sparseArray = getSparseArray(arr);
        FileWriter fileWriter = new FileWriter(path);

        try {
            for (int i = 0; i < sparseArray.length; i++) {
                fileWriter.append(ArrayUtils.join(sparseArray[i]));

                System.out.println(ArrayUtils.join(sparseArray[i]));
                if (i != sparseArray.length - 1) {
                    fileWriter.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileWriter.close();
        }

    }

    /**
     * 从指定位置 恢复稀疏数组
     * 传入的数组需要符合稀疏数组
     *
     * @param path      从指定位置恢复数组
     * @return          稀疏数组
     * @throws IOException
     */
    public static int[][] recover(String path) throws IOException {
        BufferedReader bufferReader = new BufferedReader(new FileReader(path));
        // 第一行内容
        String line;
        int[][] sparseArr = null;
        try {
            line = bufferReader.readLine();
            int[] info = ArrayUtils.toIntArray(line.split("\t"));
            sparseArr = new int[info[0]][info[1]];

            while ((line = bufferReader.readLine()) != null) {
                info = ArrayUtils.toIntArray(line.split("\t"));
                sparseArr[info[0]][info[1]] = info[2];
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferReader.close();
        }

        return sparseArr;
    }

    /**
     * 得到稀疏数组
     *
     * @param arr   原始数组
     * @return      稀疏数组
     */
    public static int[][] getSparseArray(int[][] arr) {
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] != 0) {
                    sum++;
                }
            }
        }

        int[][] sparseArr = new int[sum + 1][3];
        sparseArr[0][0] = arr.length;
        sparseArr[0][1] = arr[0].length;
        sparseArr[0][2] = sum;

        int curr = 1;
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
     * @param arr   稀疏数组
     * @return
     */
    public static int[][] getOrigArray(int[][] arr) {
        int[][] origArr = new int[arr[0][0]][arr[0][1]];

        for (int i = 1; i < arr.length; i++) {
            origArr[arr[i][0]][arr[i][1]] = arr[i][2];
        }

        return origArr;
    }
}