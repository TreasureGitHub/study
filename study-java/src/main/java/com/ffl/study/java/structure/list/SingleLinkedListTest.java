package com.ffl.study.java.structure.list;

/**
 * @author lff
 * @datetime 2020/11/30 13:29
 */
public class SingleLinkedListTest {

    public static void main(String[] args) {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        // System.out.println(list.size());
        // System.out.println(list.isEmpty());
        list.add("hello");
        list.add("world");
        list.add("java");
        list.add("scala");
        list.add("hive");

        System.out.println(list);

        System.out.println(list.remove(4));

        System.out.println(list);

        System.out.println(list.size());
        System.out.println(list.isEmpty());

        System.out.println("-------------反转前--------------");
        System.out.println(list);


        list.reverse1();
        // 反转
        System.out.println("-------------反转后--------------");
        System.out.println(list);



        list.clear();

        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }

        System.out.println(list);

        list.reverse3();
        System.out.println(list);
    }
}
