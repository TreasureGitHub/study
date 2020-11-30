package com.ffl.study.java.structure.queue;

import java.util.Arrays;

/**
 * 数组模拟队列
 *
 * @author lff
 * @datetime 2020/06/12 02:33
 */
public class ArrayQueue {

    /**
     * 表示队列最大容量
     */
    private int capacity;

    /**
     * 队列头
     * 指向队列头部，分析出front是指向队列头的前一个位置
     */
    private int front;

    /**
     * 队列尾
     * 指向队列尾部，即最后一个数据
     */
    private int rear;

    /**
     * 数组
     */
    private int[] arr;

    /**
     * @param capacity 最大容量
     */
    public ArrayQueue(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("队列长度必须为空");
        }

        this.capacity = capacity;
        front = -1; // 指向队列头部，分析出front是指向队列头的前一个位置
        rear = -1;  // 指向队列尾部，即最后一个数据

        if (capacity != 0) {
            arr = new int[capacity];
        }
    }

    /**
     * 判断队列是否满
     *
     * @return
     */
    public boolean isFull() {
        return rear == capacity - 1;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return front == rear;
    }

    /**
     * 添加数据
     *
     * @param n
     */
    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("队列已满");
            return;
        }

        // rear 后移，且赋值
        arr[++rear] = n;
    }

    /**
     * 从队列取数据
     *
     * @return
     */
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }

        return arr[++front];
    }

    /**
     * 显示队列的头部，注意，此处不是取数
     *
     * @return
     */
    public int showHead() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }

        return arr[front + 1];
    }

    /**
     * 显示队列数据
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列尾空！");
        } else {
            System.out.println(Arrays.toString(arr));
        }
    }
}
