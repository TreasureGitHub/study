package com.ffl.study.java.structure.queue;

import java.util.Arrays;

/**
 * @author lff
 * @datetime 2020/06/12 02:33
 */
public class ArrayQueue {

    // 队列最大容量
    private int maxSize;

    // 队列头
    private int front;

    // 队列尾
    private int rear;

    private int[] arr;

    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        // 队列头部，指向队列头的前一个位置
        front = -1;
        // 队列尾，指向队列尾部的数据（即是队列最后一个数据）
        rear = -1;
    }

    private boolean isFull() {
        return rear == maxSize - 1;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public boolean addQueue(int n) {
        if (isFull()) {
            return false;
        }

        rear++;
        arr[rear] = n;
        return true;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，不能取数据");
        }
        front++;
        return arr[front];
    }

    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空");
        }

        for (int i = 0; i < arr.length; i++) {
            Arrays.toString(arr);
        }
    }

    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，没有数据");
        }
        return arr[front + 1];
    }
}
