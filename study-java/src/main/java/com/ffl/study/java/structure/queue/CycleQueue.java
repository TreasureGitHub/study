package com.ffl.study.java.structure.queue;

/**
 * 数组模拟队列
 *
 * @author lff
 * @datetime 2020/06/12 02:33
 */
public class CycleQueue {

    /**
     * 表示队列最大容量
     */
    private int capacity;

    /**
     * 队列头
     * front指向队列的第一个元素，也就是说arr[font]是队列的第一个元素，front的初始值 = 0
     */
    private int front;

    /**
     * 队列尾
     * rear指向队列的最后一个元素的后一个位置，因为希望空出一个空间做约定，rear初始值 = 0
     */
    private int rear;

    /**
     * 数组
     */
    private int[] arr;

    /**
     * @param capacity 最大容量
     */
    public CycleQueue(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("队列长度必须为空");
        }

        this.capacity = capacity;
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
        return (rear + 1) % capacity == front;
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

        // 直接加入数据
        arr[rear] = n;
        // rear 后移
        rear = (rear + 1) % capacity;
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

        int tmp = arr[front];
        front = (front + 1) % capacity;
        return tmp;

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

        return arr[front];
    }

    /**
     * 显示队列数据
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列尾空！");
        } else {

            for (int i = front; i < front + size(); i++) {
                System.out.printf("arr[%d]=%d\n", i % capacity,arr[i % capacity]);
            }
        }
    }

    //
    public int size() {
        return (rear + capacity - front) % capacity;
    }
}
