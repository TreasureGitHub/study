package com.ffl.study.java.structure.list;

import com.ffl.study.common.constants.StringConstants;

/**
 * 头部节点存放数据
 *
 * @author lff
 * @datetime 2020/11/30 12:21
 */
public class SingleLinkedList<T> {

    /**
     * 容量大小
     */
    private int size = 0;

    /**
     * 头部节点
     */
    private Node head;


    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 链表长度
     *
     * @return
     */
    public int size() {
        return this.size;
    }

    /**
     * 在末尾添加数据
     *
     * @param data
     */
    public void add(T data) {
        add(size, data);
    }

    /**
     * 在指定位置添加数据
     *
     * @param index 指定下标
     * @param data  数据
     */
    public void add(int index, T data) {
        checkAddIndex(index);

        Node newNode = new Node(data);

        // 如果为空则初始化头部节点
        if (isEmpty()) {
            head = newNode;
        } else if (index == 0) { //
            Node follow = head;
            head = newNode;
            newNode.next = follow;
        } else {
            // 遍历到 指定下标的前一位
            Node temp = getNode(index - 1);

            Node follow = temp.next;
            temp.next = newNode;
            newNode.next = follow;
        }

        size++;
    }

    /**
     * 删除指定位置的数据
     *
     * @param index
     * @return
     */
    public T remove(int index) {
        checkIndex(index);

        if (isEmpty()) {
            return null;
        }

        Node toRemove;

        if (index == 0) {
            toRemove = head;
            head = head.next; // hed后移
        } else {
            Node pre = getNode(index - 1);
            toRemove = pre.next;
            pre.next = toRemove.next;
            toRemove.next = null; // 释放
        }

        size--;

        return toRemove == null ? null : toRemove.data;
    }

    /**
     * 反转  ， 常规手段
     */
    public void reverse() {
        if (isEmpty() || size == 1) {
            return;
        }

        Node pre = head;
        Node curr = head.next;
        Node follow;
        pre.next = null;

        while (curr.next != null) {
            follow = curr.next;
            curr.next = pre;

            pre = curr;
            curr = follow;
        }

        head = curr;
        head.next = pre;
    }


    public void clear(){
        this.size = 0;
        this.head =  null;
    }

    /**
     * 递归手段
     */
    public void reverse1() {
        if (isEmpty() || size == 1) {
            return;
        }

        reverse1(head,head.next);
    }

    private void reverse1(Node pre ,Node curr) {
        // 退出递归条件
        if(curr.next == null){
            curr.next = pre;
            head = curr;
            return;
        }

        reverse1(curr,curr.next);

        curr.next = pre;
        pre.next = null;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return StringConstants.LEFT_SQUARE + StringConstants.RIGHT_SQUARE;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(StringConstants.LEFT_SQUARE);

        Node curr = head;
        while (true) {

            sb.append(curr.data);
            if (curr.next == null) {
                break;
            }

            sb.append(StringConstants.COMMA);
            curr = curr.next;
        }

        sb.append(StringConstants.RIGHT_SQUARE);
        return sb.toString();
    }

    /**
     * 得到指定位置的 Node节点
     *
     * @param index 指定下标
     * @return
     */
    private Node getNode(int index) {
        checkIndex(index);

        Node temp = head;

        while (--index >= 0) {
            temp = temp.next;
        }

        return temp;
    }

    /**
     * 检查下标
     * <p>
     * index 下标范围为 [0,size-1]
     *
     * @param index 待检查下标
     */
    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * 检查add下标
     * <p>
     * index 下标范围为 [0,size]
     *
     * @param index 待检查下标
     */
    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }


    private class Node {

        /**
         * 实际存放数据
         */
        private T data;

        /**
         * 下一个元素
         */
        private Node next;

        private Node(T data) {
            this.data = data;
        }
    }
}
