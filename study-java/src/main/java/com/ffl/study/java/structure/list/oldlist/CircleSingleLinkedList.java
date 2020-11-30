package com.ffl.study.java.structure.list.oldlist;

import com.ffl.study.common.constants.StringConstants;

import java.util.Iterator;

import static com.ffl.study.common.constants.StringConstants.LEFT_SQUARE;
import static com.ffl.study.common.constants.StringConstants.RIGHT_SQUARE;

/**
 * @author lff
 * @datetime 2020/06/13 10:48
 */
public class CircleSingleLinkedList<T> implements Iterable<T> {

    private Node first;

    private Node end;

    private int size = 0;

    public void add(T data) {
        Node newNode = new Node(data);

        // 如果没有数据，则让 first = new Node
        if (size == 0) {
            first = newNode;
            first.next = first;
        } else {
            end.next = newNode;
            newNode.next = first;
        }

        end = newNode;

        size++;
    }

    /**
     * 从 start 位置开始 开始算起
     * <p>
     * 数n下弹出数据
     *
     * @param startIndex
     * @param cnt
     */
    public CircleSingleLinkedList getJosepfuList(int startIndex, int cnt) {
        checkAddRange(startIndex);

        // 先定位到 start 位置
        for (int i = 0; i < startIndex - 1; i++) {
            first = first.next;
            end = end.next;
        }

        CircleSingleLinkedList<T> newList = new CircleSingleLinkedList<>();

        while (size > 1) {
            // 先定位到 start 位置
            for (int i = 0; i < cnt - 1; i++) {
                first = first.next;
                end = end.next;
            }

            // 此时first指向的节点就是要出列的节点
            newList.add(first.data);

            first = first.next;
            end.next = first;
            size--;
        }

        newList.add(first.data);
        clear();

        return newList;
    }

    public int size() {
        return size;
    }

    public void clear(){
        first = null;
        end = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node curr;
            int cnt = 1;

            @Override
            public boolean hasNext() {
                return cnt <= size;
            }

            @Override
            public T next() {
                if (curr == null) {
                    curr = first;
                } else {
                    curr = curr.next;
                }
                cnt++;

                return curr.data;
            }
        };
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