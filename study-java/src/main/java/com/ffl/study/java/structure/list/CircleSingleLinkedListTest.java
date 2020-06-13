package com.ffl.study.java.structure.list;

/**
 * @author lff
 * @datetime 2020/06/12 21:09
 */
public class CircleSingleLinkedListTest {

    public static void main(String[] args) {
        // ArrayList<String> list = Lists.newArrayList();
        CircleSingleLinkedList<String> list = new CircleSingleLinkedList<>();

        for (int i = 1; i <= 100; i++) {
            list.add(String.valueOf(i));
        }

        System.out.println(list);

        CircleSingleLinkedList josepfuList = list.getJosepfuList(2, 5);


        System.out.println(josepfuList);

        System.out.println();
    }
}
