package com.ffl.study.java.structure.hashtable;

import java.util.Scanner;

/**
 * @author lff
 * @datetime 2020/11/30 09:01
 */
public class HashTableTest {

    public static void main(String[] args) {
        // 创建hash表
        HashTable hashTable = new HashTable(7);

        // hashTable.add(new Emp(1,"tome"));
        // hashTable.list();


        // 接收数据
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;

        String key;

        while (loop) {
            System.out.println("add： 添加雇员");
            System.out.println("list：显示雇员");
            System.out.println("exit：退出系统");
            System.out.println("find：查找");
            System.out.println("remove：删除");
            key = scanner.next();

            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入姓名");
                    String name = scanner.next();
                    Emp emp = new Emp(id, name);
                    hashTable.add(emp);
                    break;
                case "list":
                    hashTable.list();
                    break;
                case "find":
                    System.out.println("输入id");
                    int findId = scanner.nextInt();
                    Emp empById = hashTable.findById(findId);
                    System.out.println(empById);
                    break;
                case "remove":
                    System.out.println("输入id");
                    int removeId = scanner.nextInt();
                    Emp removeById = hashTable.removeById(removeId);
                    System.out.println(removeById);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}