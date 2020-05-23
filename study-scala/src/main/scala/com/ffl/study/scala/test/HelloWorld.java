package com.ffl.study.scala.test;

/**
 * @author lff
 * @datetime 2020/05/20 01:46
 *
 * 理解为scala在运行时做了一个包装
 */
public class HelloWorld {

    public static void main(String[] args) {
        HelloWorld$.MODULE$.main(args);
    }
}


final class HelloWorld$ {

    public static final HelloWorld$ MODULE$;

    static {
        MODULE$ = new HelloWorld$();
    }

    public void main(String[] args){
        System.out.println("hello,scala, idea...");
    }
}