package com.ffl.study.java.structure.stack.old;

import com.ffl.study.common.constants.StringConstants;

/**
 * @author lff
 * @datetime 2020/06/13 17:33
 */
public class ArrayStack {

    // 栈的大小
    private int maxSize;

    // 数组，模拟栈
    private int[] stack;

    // 栈顶
    private int top = -1;


    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    public int peek(){
        return stack[top];
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(int value) {
        if (isFull()) {
            throw new RuntimeException("栈满");
        }

        top++;
        stack[top] = value;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("空栈");
        }

        int value = stack[top];
        top--;
        return value;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = top; i >= 0; i--) {
            sb.append(stack[i]);
            if (i != 0) {
                sb.append(StringConstants.COMMA);
            }
        }

        return StringConstants.LEFT_SQUARE + sb.toString() + StringConstants.RIGHT_SQUARE;
    }
}