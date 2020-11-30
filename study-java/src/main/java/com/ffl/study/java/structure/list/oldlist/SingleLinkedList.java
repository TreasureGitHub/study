package com.ffl.study.java.structure.list.oldlist;

import java.util.Iterator;

import static com.ffl.study.common.constants.StringConstants.*;

/**
 * 头部节点不存放任何数据
 *
 * @author lff
 * @datetime 2020/06/12 20:17
 *
 * 单向链表
 */
public class SingleLinkedList<T> implements Iterable<T> {

    /**
     * 头部节点，不存放任何数据
     */
    private Node head = new Node(null);

    /**
     * 长度
     */
    private int size = 0;

    /**
     * 添加数据，默认在末尾
     *
     * @param data
     */
    public void add(T data) {
        add(size, data);
    }

    /**
     * 在 index 位置加入数据
     *
     * @param index
     * @param data
     */
    public void add(int index, T data) {
        checkAddRange(index);

        // 前一个节点
        Node preNode;

        // 保存数据
        Node newNode = new Node(data);

        // 如果是空 或者 index = 0，preNode 为head ,否则为 getNode(index - 1)
        if (isEmpty() || index == 0) {
            preNode = head;
        } else {
            preNode = getNode(index - 1);
        }

        // 如果index 不是末尾，则查询 index 位置的node，并将 node 的next指向 nextNode
        if (index < size) {
            Node nextNode = preNode.next;
            newNode.next = nextNode;
        }

        // 前一个node 的next指向新节点
        preNode.next = newNode;

        size++;
    }

    /**
     * 反转节点
     */
    public void reverse() {
        if (size == 0 || size == 1) {
            return;
        }

        // 新建一个起始节点
        Node newHead = new Node(null);

        // 定义辅助的指针(变量)，帮助我们遍历原来的表
        Node currNode = head.next;
        // 指向当前节点的下一个节点
        Node nextNode;

        while (currNode != null) {
            // 将当前节点的下一个赋值给nextNode，后续要使用
            nextNode = currNode.next;
            // currNode 插入到  newHead 后面，当成第一个元素
            currNode.next = newHead.next;
            newHead.next = currNode;

            // 让currNode 后移
            currNode = nextNode;
        }

        // 沿用原来老的head，释放newHead
        head.next = newHead.next;
        newHead.next = null;
    }

    /**
     * 删除尾部 节点
     */
    public void remove() {
        remove(size - 1);
    }

    /**
     * 删除index 位置的节点
     *
     * 原理为将index 位置的 上一个节点的next 指向 index下一个节点
     *
     * @param index
     */
    public void remove(int index) {
        checkRange(index);

        // 上一个节点和下一个节点
        Node preNode;
        Node nextNode;

        // 上一个节点
        if (index == 0) {
            preNode = head;
        } else {
            preNode = getNode(index - 1);
        }

        // 如果index为最末尾节点，则下一个节点为空
        if (index == size - 1) {
            nextNode = null;
        } else {
            nextNode = preNode.next.next;
            preNode.next.next = null;
        }

        preNode.next = nextNode;
        size--;
    }


    /**
     * 清除数据
     */
    public void clear() {
        size = 0;
        head.next = null;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * size
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 迭代器
     *
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        Iterator<T> resIterator = new Iterator<T>() {

            Node curr = head;

            @Override
            public boolean hasNext() {
                return curr.next != null;
            }

            @Override
            public T next() {
                T resData = curr.next.data;
                curr = curr.next;
                return resData;
            }
        };

        return resIterator;
    }

    /**
     * 得到 第 index 位置的值
     *
     * @param index
     * @return
     */
    public T get(int index) {
        checkRange(index);
        return getNode(index).data;
    }

    /**
     * 得到 第 index 位置的 Node
     *
     * @param index
     * @return
     */
    private Node getNode(int index) {
        // 检查下标
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }

        Node curr = head;

        while (index >= 0 && curr.next != null) {
            index--;
            curr = curr.next;
        }

        return curr;
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        StringBuffer sb = new StringBuffer();

        while (it.hasNext()) {
            sb.append(it.next().toString());
            if (it.hasNext()) {
                sb.append(COMMA);
            }
        }

        return LEFT_SQUARE + sb.toString() + RIGHT_SQUARE;
    }

    /**
     * 检查 get 下标范围
     *
     * @param index
     */
    private void checkRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size));
        }
    }

    /**
     * 检查 add 下标范围
     *
     * @param index
     */
    private void checkAddRange(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size));
        }
    }

    /**
     * 节点信息 ，用于存放数据
     */
    private class Node {

        /**
         * 当前数据
         */
        private T data;

        /**
         * next 指向 下一个节点
         */
        private Node next;

        private Node(T data) {
            this.data = data;
        }
    }
}
