package com.ffl.study.java.structure.stack.old;

/**
 * @author lff
 * @datetime 2020/06/13 17:53
 */
public class ArrayStackTest {

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(10);
        arrayStack.push(2);
        arrayStack.push(3);
        arrayStack.push(4);
        arrayStack.push(5);
        arrayStack.push(6);
        arrayStack.push(7);
        System.out.println(arrayStack);

        arrayStack.pop();
        arrayStack.pop();
        arrayStack.pop();
        arrayStack.pop();
        System.out.println(arrayStack);
    }
}
