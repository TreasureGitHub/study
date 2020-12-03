package com.ffl.study.java.structure.stack;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/12/02 23:16
 */
public class ArrayStack {

    // 表示当前数据位置
    private int position = -1;

    // 数组，模拟栈
    private int[] stack;

    // 容量
    private int capacity;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        stack = new int[capacity];
    }

    /**
     * 是否已满
     *
     * @return
     */
    public boolean isFull() {
        return position == capacity -1;
    }

    public boolean isEmpty() {
        return position == -1;
    }

    /**
     * 推入数据
     *
     * @param data
     */
    public void push(int data) {
        if (isFull()) {
            throw new RuntimeException("栈已满！");
        }

        stack[++position] = data;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈为空！");
        }
        return stack[position--];
    }

    public int peek() {
        return stack[position];
    }

    @Override
    public String toString() {
        return "ArrayStack{" + Arrays.toString(Arrays.copyOfRange(stack,0,position + 1)) +
                '}';
    }
}
