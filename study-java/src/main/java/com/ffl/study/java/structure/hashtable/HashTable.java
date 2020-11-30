package com.ffl.study.java.structure.hashtable;

/**
 * @author lff
 * @datetime 2020/11/30 09:58
 */
public class HashTable {

    /**
     * empLinkedListArray
     */
    private EmpLinkedList[] empLinkedListArray;

    /**
     * 容量
     */
    private int size;

    public HashTable(int size) {
        this.size = size;
        empLinkedListArray = new EmpLinkedList[size];
        // 分别初始化每个链表
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    /**
     * 添加雇员
     *
     * @param emp
     */
    public void add(Emp emp) {
        // 得到散列值
        int empLinkedListNo = hash(emp.getId());
        // 将emp 添加到对应的 链表中
        empLinkedListArray[empLinkedListNo].add(emp);
    }


    /**
     * 遍历所有的链表，遍历hash表
     */
    public void list() {
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i);
        }
    }

    /**
     * 根据id查找
     *
     * @param no
     * @return
     */
    public Emp findById(int no) {
        int hashNo = hash(no);
        EmpLinkedList empLinkedList = empLinkedListArray[hashNo];

        Emp emp = empLinkedList.findById(no);

        if (emp == null) {
            System.out.println("查无此人！");
            return null;
        }

        return emp;
    }

    /**
     * 根据id进行删除
     *
     * @param no 编号
     * @return
     */
    public Emp removeById(int no) {
        int hashNo = hash(no);
        EmpLinkedList empLinkedList = empLinkedListArray[hashNo];

        Emp emp = empLinkedList.removeById(no);


        if (emp == null) {
            System.out.println("查无此人，无法删除！");
            return null;
        }

        return emp;
    }

    /**
     * 散列函数，使用简单的取模算法
     *
     * @param id
     * @return
     */
    private int hash(int id) {
        return id % size;
    }
}
