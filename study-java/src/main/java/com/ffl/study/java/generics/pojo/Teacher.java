package com.ffl.study.java.generics.pojo;

import lombok.Data;

/**
 * @author lff
 * @datetime 2020/01/04 12:51
 */
@Data
public class Teacher {

    public Teacher(String name, Integer age, Integer sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }


    private String name;

    private Integer age;

    private Integer sex;
}
