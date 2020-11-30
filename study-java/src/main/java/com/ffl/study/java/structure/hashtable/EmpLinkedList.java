package com.ffl.study.java.structure.hashtable;

import com.ffl.study.common.constants.StringConstants;

/**
 * 表示链表
 *
 * @author lff
 * @datetime 2020/11/30 09:39
 */
public class EmpLinkedList {

    /**
     * 头指针，执行第一个Emp，因此我们这个链表的head 是直接指向 第一个Emp
     */
    private Emp head;

    /**
     * 添加雇员到链表
     * 说明：
     * 1.假定，当添加雇员时，id是自增长的，即id的分配总是从小到大，因此直接将雇员添加到最后即可
     *
     * @param emp 待添加雇员
     */
    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }

        // 如果不是第一个雇员，则使用辅助指针定位到最后
        Emp currEmp = head;
        while (currEmp.getNext() != null) {
            currEmp = currEmp.getNext(); // 后移
        }

        // currEmp 为最后一个值
        currEmp.setNext(emp);
    }

    /**
     * 遍历链表
     */
    public void list(int no) {
        if (head == null) {
            System.out.println("第" + no + "条链表链表为空~");
            return;
        }

        System.out.print("第" + no + "条链表信息为：");

        Emp curr = head;
        while (true) {
            System.out.print(curr + StringConstants.TAB);

            if (curr.getNext() == null) {
                break;
            } else {
                curr = curr.getNext();
            }
        }

        System.out.println();
    }

    /**
     * 根据id查找
     *
     * @param no 待查找的编号
     * @return
     */
    public Emp findById(int no) {
        if (head == null) {
            System.out.println("链表为空！");
            return null;
        }

        Emp curr = head;

        while (true) {
            if (curr.getId() == no) {
                return curr;
            }

            // 如果已经到最后了，则直接返回空
            if (curr.getNext() == null) {
                return null;
            }

            curr = curr.getNext();
        }
    }

    /**
     * 根据id进行删除
     *
     * @param no
     * @return
     */
    public Emp removeById(int no) {
        if (head == null) {
            System.out.println("链表为空！");
            return null;
        }

        // 当前指针
        Emp curr = head;
        Emp pre = null;

        while (true) {
            if (curr.getId() == no) {
                break;
            }

            if (curr.getNext() == null) {
                curr = null;
                break;
            }

            pre = curr;
            curr = curr.getNext();
        }

        if (curr == null) {
            return null;
        }

        if (curr == head) { // 如果是头部，则head指向下一个
            head = curr.getNext();
        } else { // 否则上一个元素的next直接指向下一个元素，跳过当前元素
            pre.setNext(curr.getNext());
        }

        return curr;
    }

}
