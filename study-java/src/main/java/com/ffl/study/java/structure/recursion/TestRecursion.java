package com.ffl.study.java.structure.recursion;

/**
 * @author lff
 * @datetime 2020/06/14 23:18
 */
public class TestRecursion {

    public static void main(String[] args) {
        test(4);
        System.out.println(f(6));
    }

    /**
     * 1.打印问题
     *
     * @param n
     */
    private static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }

        System.out.println("n = " + n);
    }


    /**
     * 2.阶乘问题
     *
     * @param n
     * @return
     */
    private static int f(int n) {
        if (n == 1) {
            return 1;
        }

        return f(n - 1) * n;
    }
}
