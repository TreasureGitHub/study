package test;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

/**
 * @author lff
 * @datetime 2021/01/22 19:54
 */
public class Test {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();

        list.add("hello");
        list.add("world");
        list.add("spark");
        list.add("scala");
        list.add("hbase");
    }

    /**
     * 判断链表是否有环，如果有的话则第一个节点。 如果链表无环，则返回 NULL
     *
     * @param list
     */
    public static String cycle(List<String> list){
        if(list == null){
            return null;
        }

        Iterator<String> iterator = list.iterator();

        String tmp = list.get(0);
        while (iterator.hasNext()){
            String next = iterator.next();
            if(tmp == next){
                return tmp;
            }
        }

        return null;
    }
}
