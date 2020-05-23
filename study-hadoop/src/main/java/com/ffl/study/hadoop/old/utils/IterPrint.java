package com.ffl.study.hadoop.old.utils;

import java.util.LinkedList;

/**
 * @author liufeifei
 * @date 2018/05/11
 */
public class IterPrint {

    public static <T> void print(Iterable<T> it) {

        LinkedList<String> list = new LinkedList();
        System.out.print("< ");
        for(T t:it) {
            list.add(t.toString());
        }
        System.out.print(String.join(",",list));
        System.out.println(" >");

    }

}
