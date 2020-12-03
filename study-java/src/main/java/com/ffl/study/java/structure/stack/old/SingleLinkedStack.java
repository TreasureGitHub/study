package com.ffl.study.java.structure.stack.old;

import com.ffl.study.java.structure.list.oldlist.SingleLinkedList;

/**
 * @author lff
 * @datetime 2020/06/13 18:00
 *
 * 在首部进行插入和弹出，减少链表遍历
 */
public class SingleLinkedStack<T> {

    // 栈的大小
    private int maxSize;

    // 数组，模拟栈
    private SingleLinkedList<T> stack;

    // 栈顶
    private int top = -1;

    public SingleLinkedStack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new SingleLinkedList<>();
    }

    public boolean isFull() {
        return top == maxSize;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(T data) {
        if (top == maxSize - 1) {
            throw new RuntimeException("栈满");
        }

        stack.add(0, data);

        top++;
    }

    public T pop() {
        if (top == -1) {
            throw new RuntimeException("栈空");
        }
        T value = stack.get(top);
        stack.remove(top);

        top--;
        return value;
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
