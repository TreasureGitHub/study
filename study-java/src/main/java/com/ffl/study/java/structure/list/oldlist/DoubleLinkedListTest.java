package com.ffl.study.java.structure.list.oldlist;

/**
 * @author lff
 * @datetime 2020/06/12 21:09
 */
public class DoubleLinkedListTest {

    public static void main(String[] args) {
        // ArrayList<String> list = Lists.newArrayList();
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        // 添加
        list.add("hello");
        list.add("world");
        list.add("scala");
        list.add("hive");
        list.add(0,"tmp");
        list.add(2,"spark");


        System.out.println(list);

        // // 删除
        list.remove(1);
        list.remove();

        System.out.println(list);
        System.out.println(list.get(3));

        System.out.println(list);

        list.clear();

        System.out.println(list);
    }
}
