package com.ffl.study.java.structure.stack;

import static com.ffl.study.common.constants.CharConstants.*;

/**
 * @author lff
 * @datetime 2020/06/13 18:32
 */
public class Calculator {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        // int res = calculator.calculator("700+2*6-4");
        int res = calculator.calculator("9-7*1*1+2");
        System.out.println(res);
    }

    public int calculator(String expression) {
        // 创建两个栈，数栈 和 符号栈
        ArrayStack numStack = new ArrayStack(10);
        ArrayStack operStack = new ArrayStack(10);

        int index = 0;
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' ';

        String keepNum = "";

        while (index < expression.length()) {
            // 依次扫描 每一个字符
            ch = expression.charAt(index);
            if (isOper(ch)) {
                // 判断符号栈是否为空
                if (!operStack.isEmpty()) {
                    if (priority(ch) <= priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = cal(num1, num2, oper);
                        numStack.push(res);
                        operStack.push(ch);
                    } else {
                        operStack.push(ch);
                    }
                } else {
                    operStack.push(ch);
                }
            } else {
                // 不能直接入栈，可能是多维，应该向表达式的后面再看一位
                keepNum += ch;

                if(index == expression.length() - 1){
                    numStack.push(Integer.parseInt(keepNum));
                } else {
                    if (isOper(expression.charAt(index + 1))) {
                        numStack.push(Integer.parseInt(keepNum));
                        keepNum = "";
                    }
                }
            }
            index++;
        }


        while (true) {
            if (operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = cal(num1, num2, oper);
            numStack.push(res);
        }

        return numStack.pop();
    }

    // 判断优先级
    private int priority(int oper) {
        if (oper == MULTIPLY || oper == DIVIDE) {
            return 1;
        } else if (oper == PLUS || oper == MINUS) {
            return 0;
        } else {
            return -1;
        }
    }

    // 判断运算符
    private boolean isOper(char value) {
        return value == PLUS || value == MINUS || value == MULTIPLY || value == DIVIDE;
    }

    public int cal(int num1, int num2, int oper) {
        int res = 0;

        switch (oper) {
            case PLUS:
                res = num1 + num2;
                break;
            case MINUS:
                res = num2 - num1;
                break;
            case MULTIPLY:
                res = num1 * num2;
                break;
            case DIVIDE:
                res = num2 / num1;
                break;
            default:
                break;
        }

        return res;
    }


}
