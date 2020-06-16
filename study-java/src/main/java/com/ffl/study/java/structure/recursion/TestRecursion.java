package com.ffl.study.java.structure.recursion;

/**
 * @author lff
 * @datetime 2020/06/14 23:18
 */
public class TestRecursion {

    public static void main(String[] args) {
        test(4);
    }

    /**
     * test n
     *
     * @param n
     */
    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }

        System.out.println("n = " + n);
    }
}
