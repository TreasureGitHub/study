package com.ffl.lean.java.generics.pojo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author lff
 * @datetime 2020/01/04 15:26
 */
public class TestSuperClass {

    public static void main(String[] args) {

        System.out.println("Student.class.getSuperclass()\t"
                + Student.class.getSuperclass());
        System.out.println("Student.class.getGenericSuperclass()\t"
                + Student.class.getGenericSuperclass());

        Type genericSuperclass = Student.class.getGenericSuperclass();

        System.out.println("ParameterizedType      " + (ParameterizedType)genericSuperclass);

        Type[] typeArr = ((ParameterizedType)genericSuperclass).getActualTypeArguments();
        System.out.println("Type[] typeArr  " + typeArr[0]);

        System.out.println("-------------------------------");

        System.out.println("Test.class.getSuperclass()\t"
                + TestSuperClass.class.getSuperclass());
        System.out.println("Test.class.getGenericSuperclass()\t"
                + TestSuperClass.class.getGenericSuperclass());

        System.out.println("Object.class.getGenericSuperclass()\t"
                + Object.class.getGenericSuperclass());
        System.out.println("Object.class.getSuperclass()\t"
                + Object.class.getSuperclass());

        System.out.println("void.class.getSuperclass()\t"
                + void.class.getSuperclass());
        System.out.println("void.class.getGenericSuperclass()\t"
                + void.class.getGenericSuperclass());

        System.out.println("int[].class.getSuperclass()\t"
                + int[].class.getSuperclass());
        System.out.println("int[].class.getGenericSuperclass()\t"
                + int[].class.getGenericSuperclass());
    }
}

class Person<T> {

}

class Student extends Person<TestSuperClass> {

}