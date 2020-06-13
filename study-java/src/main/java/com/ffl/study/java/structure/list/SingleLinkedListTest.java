package com.ffl.study.java.structure.list;

/**
 * @author lff
 * @datetime 2020/06/12 21:09
 */
public class SingleLinkedListTest {

    public static void main(String[] args) {
        // ArrayList<String> list = Lists.newArrayList();
        SingleLinkedList<String> list = new SingleLinkedList<>();

        long l1 = System.currentTimeMillis();

        list.add("hello");
        list.add("world");
        list.add("spark");
        list.add("scala");
        list.add("hive");

        printList(list,0);

        // list.reverse();
        //
        // long l2 = System.currentTimeMillis();
        // System.out.println(list);
        // System.out.println(l2 - l1);
        //
        // list.clear();

    }


    public static void printList(SingleLinkedList list,int n){
        if(n >= list.size() - 1){
            return;
        }

        printList(list,++n);
        System.out.println(list.get(n));
    }
}
