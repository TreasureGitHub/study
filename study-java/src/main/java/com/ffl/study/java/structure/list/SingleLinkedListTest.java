package com.ffl.study.java.structure.list;

/**
 * @author lff
 * @datetime 2020/06/12 21:09
 */
public class SingleLinkedListTest {

    public static void main(String[] args) {
        // ArrayList<String> list = Lists.newArrayList();
        SingleLinkedList<Person> list = new SingleLinkedList<>();

        // 添加
        list.add(new Person("2","张三"));
        list.add(new Person("4","李四"));
        list.add(new Person("5","王五"));
        list.add(new Person("6","赵六"));
        list.add(0,new Person("1","田七"));
        list.add(2,new Person("3","猪八"));


        System.out.println(list);

        // 删除
        list.remove(1);
        list.remove();

        SingleLinkedList list1 = list;


        System.out.println(list);
        System.out.println(list.get(3));

        list.reverse();

        System.out.println(list);

        list.clear();

        System.out.println(list);
    }
}

class Person {

    private String no;

    private String name;

    public Person(String no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "no='" + no + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
