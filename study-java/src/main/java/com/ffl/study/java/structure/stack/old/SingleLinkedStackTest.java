package com.ffl.study.java.structure.stack.old;

/**
 * @author lff
 * @datetime 2020/06/13 17:53
 */
public class SingleLinkedStackTest {

    public static void main(String[] args) {
        SingleLinkedStack<String> stack = new SingleLinkedStack<>(6);
        stack.push("2");
        stack.push("3");
        stack.push("4");
        stack.push("5");
        stack.push("6");
        stack.push("7");
        System.out.println(stack);

        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        System.out.println(stack);
    }
}
