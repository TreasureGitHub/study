package com.ffl.study.java.structure.queue;

import java.util.Scanner;

/**
 * @author lff
 * @datetime 2020/11/29 22:22
 */
public class ArrayQueueTest {

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);
        char key;

        // 接收数据
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;

        while (loop) {
            System.out.println("s(show)：显示队列");
            System.out.println("e(exit)：退出程序");
            System.out.println("a(add)：添加数据到队列");
            System.out.println("g(get)：从队列取出数据");
            System.out.println("h(head)：查看头部数据");
            key = scanner.next().charAt(0); // 接收一个字符串

            switch (key) {
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("输入一个数据");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.println("取出数据是：" + res);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = arrayQueue.showHead();
                        System.out.println("队列头部数据为：" + res);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    loop = false;
                    scanner.close();
                    System.out.println("程序退出！");
                default:
                    scanner.close();
                    break;
            }
        }

    }
}
