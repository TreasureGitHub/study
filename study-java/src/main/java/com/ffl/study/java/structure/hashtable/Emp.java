package com.ffl.study.java.structure.hashtable;

import lombok.Data;

/**
 * 表示雇员
 *
 * @author lff
 * @datetime 2020/11/30 09:13
 */
@Data
public class Emp {

    /**
     * id
     */
    private int id;

    /**
     * 姓名
     */
    private String name;

    /**
     * next 默认为null
     */
    private Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
