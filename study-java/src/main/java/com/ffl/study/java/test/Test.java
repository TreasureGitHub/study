package com.ffl.study.java.test;

import java.util.Stack;

/**
 * @author lff
 * @datetime 2020/12/06 18:58
 */
public class Test {

    public static void main(String[] args) {

        Solution solution = new Solution();
        solution.push(1);
        solution.push(2);
        System.out.println(solution.pop());

        solution.push(4);
        solution.push(3);

        System.out.println(solution.pop());
        System.out.println(solution.pop());
        System.out.println(solution.pop());
    }
}

class Solution {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if(stack2.size()<=0){
            while(stack1.size()!=0){
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
}