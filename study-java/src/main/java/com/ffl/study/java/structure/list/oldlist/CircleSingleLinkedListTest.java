package com.ffl.study.java.structure.list.oldlist;

/**
 * @author lff
 * @datetime 2020/06/12 21:09
 */
public class CircleSingleLinkedListTest {

    public static void main(String[] args) {
        CircleSingleLinkedList<String> list = new CircleSingleLinkedList<>();

        for (int i = 1; i <= 6; i++) {
            list.add(String.valueOf(i));
        }

        // System.out.println(list);

        CircleSingleLinkedList josepfuList = list.getJosepfuList(1, 5);


        System.out.println(josepfuList);

        // System.out.println();
    }
}
