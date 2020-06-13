package com.ffl.study.java.structure.list;

import com.ffl.study.common.constants.StringConstants;

import java.util.Iterator;

import static com.ffl.study.common.constants.StringConstants.LEFT_SQUARE;
import static com.ffl.study.common.constants.StringConstants.RIGHT_SQUARE;

/**
 * @author lff
 * @datetime 2020/06/12 20:17
 * <p>
 * 双向链表
 */
public class DoubleLinkedList<T> implements Iterable<T> {

    /**
     * 头部节点，不存放任何数据
     */
    private Node head = new Node(null);

    /**
     * 尾部节点，不存放任何数据
     */
    private Node end = new Node(null);

    /**
     * 长度
     */
    private int size = 0;

    public DoubleLinkedList() {
        // 调整指向
        head.next = end;
        end.pre = head;
    }

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
        // 保存数据
        checkAddRange(index);

        Node preNode;
        Node newNode = new Node(data);

        // 如果是空 或者 index = 0，preNode 为head ,否则为 getNode(index - 1)
        if (isEmpty() || index == 0) {
            preNode = head;
        } else {
            preNode = getNode(index - 1);
        }

        // 如果index 不是末尾，则查询 index 位置的node，并将 node 的next指向 nextNode
        Node nextNode = preNode.next;

        // 调整指向
        newNode.pre = preNode;
        newNode.next = nextNode;
        preNode.next = newNode;
        nextNode.pre = newNode;

        size++;
    }

    /**
     * 反转节点
     * 类似 SingleLinkedList
     */
    public void reverse() {

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
     * @param index
     */
    public void remove(int index) {
        checkRange(index);

        Node preNode;

        // 上一个节点
        if (index == 0) {
            preNode = head;
        } else {
            preNode = getNode(index - 1);
        }

        Node nextNode = preNode.next.next;

        preNode.next = nextNode;
        nextNode.pre = preNode;
        size--;
    }


    /**
     * 清除数据
     */
    public void clear() {
        size = 0;
        head.next = end;
        end.pre = head;
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

            Node tmp = head;

            @Override
            public boolean hasNext() {
                return tmp.next != end;
            }

            @Override
            public T next() {
                T resData = tmp.next.data;
                tmp = tmp.next;
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

        Node tmp = head;

        while (index >= 0) {
            if (tmp.next == end) {
                return tmp;
            }

            index--;
            tmp = tmp.next;
        }

        return tmp;
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        StringBuffer sb = new StringBuffer();

        while (it.hasNext()) {
            sb.append(it.next().toString());
            if (it.hasNext()) {
                sb.append(StringConstants.COMMA);
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
         * 上一个节点
         */
        private Node pre;

        /**
         * 下一个节点
         */
        private Node next;

        private Node(T data) {
            this.data = data;
        }
    }
}
