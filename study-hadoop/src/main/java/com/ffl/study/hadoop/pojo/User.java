package com.ffl.study.hadoop.pojo;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author liufeifei
 * @date 2018/05/20
 */
public class User implements WritableComparable<User> {

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 写入 数据
     *
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        name = in.readUTF();
        age = in.readInt();
    }

    /**
     * 读取数据
     *
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
    }

    /**
     * 比较方法
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(User o) {
        int res1 = Integer.compare(this.age,o.age);
        int res2 = this.name.compareTo(o.name);

        // 先比较年龄，在比较姓名
        return res1 != 0 ? res1 : - res2;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + age;
    }

    @Override
    public boolean equals(Object obj) {

        if( !(obj instanceof User)) {
            return false;
        }

        User o = (User) obj;
        return this.name == o.name && this.age == o.age;
    }

    /**
     * 输出值
     *
     * @return
     */
    @Override
    public String toString() {
        return name + "\t" +  age;
    }

}
