package com.ffl.study.java.structure.stack;

/**
 * @author lff
 * @datetime 2020/12/02 23:24
 */
public class ArrayStackTest {

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(5);
        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);
        arrayStack.push(4);
        arrayStack.push(5);
        // arrayStack.push(6);

        arrayStack.pop();
        arrayStack.pop();
        arrayStack.pop();
        arrayStack.pop();
        arrayStack.pop();
        // arrayStack.pop();

        System.out.println(arrayStack);
    }
}