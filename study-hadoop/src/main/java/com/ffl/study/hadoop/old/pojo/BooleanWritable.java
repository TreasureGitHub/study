package com.ffl.study.hadoop.old.pojo;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import static org.apache.hadoop.io.WritableComparator.readInt;

/**
 * @author liufeifei
 * @date 2018/05/19
 */
public class BooleanWritable implements WritableComparable {

    private boolean value;

    public BooleanWritable() {
    }

    public BooleanWritable(boolean value) {
        set(value);
    }

    public void set(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return this.value;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        value = in.readBoolean();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeBoolean(value);
    }

    @Override
    public boolean equals(Object o) {
        if ( !(o instanceof  BooleanWritable)) {
            return false;
        }

        BooleanWritable other = (BooleanWritable)o;
        return this.value == other.value;
    }

    @Override
    public int hashCode() {
        return value ? 0 : 1;
    }

    @Override
    public int compareTo(Object o) {
        boolean a = this.value;
        boolean b = ((BooleanWritable)o).value;
        return (a == b) ? 0 : (a == false) ? -1 : 1;
    }

    @Override
    public String toString() {
        return Boolean.toString(get());
    }

    public static class Comparator extends WritableComparator {

        public Comparator(){
            super(BooleanWritable.class);
        }
    }

    public int compare(byte[] b1, int s1, int l1,
                       byte[] b2, int s2, int l2) {
        boolean a = (readInt(b1, s1) == 1) ? true : false;
        boolean b = (readInt(b1, s1) == 1) ? true : false;
        return ((a == b) ? 0 : ( a == false) ? -1 : 1);
    }

    static {
        WritableComparator.define(BooleanWritable.class,new Comparator());
    }

}
