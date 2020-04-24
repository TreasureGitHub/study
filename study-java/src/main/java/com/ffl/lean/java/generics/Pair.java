package com.ffl.lean.java.generics;

import lombok.Data;

/**
 * @author lff
 * @datetime 2020/01/03 23:02
 */
@Data
public class Pair<T> {

    private T first;

    private T second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public static void main(String[] args) {
        String[] words = {"Mary", "had", "a", "little", "lamb"};
    }
}
