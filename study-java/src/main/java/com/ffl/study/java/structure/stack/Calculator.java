package com.ffl.study.java.structure.stack;

import com.ffl.study.common.constants.CharConstants;
import com.ffl.study.common.constants.StringConstants;

/**
 * @author lff
 * @datetime 2020/12/03 00:22
 */
public class Calculator {

    public static void main(String[] args) {
        String expression = "35+2*6-2";
        System.out.println(cal(expression));
    }

    public static int cal(String expression) {

        CalculatorStack numStack = new CalculatorStack(10);
        CalculatorStack operStack = new CalculatorStack(10);

        int index = -1; // 用于扫描下标
        int num1;
        int num2;
        int oper = 0;
        int res = 0;
        char ch; // 将每次扫描得到的char放入此
        String keepNum = StringConstants.EMPTY; // 用于拼接多位数

        while (++index < expression.length()) {
            ch = expression.charAt(index);

            if (operStack.isOper(ch)) {

                // 如果为空，直接入栈
                if (operStack.isEmpty()) {
                    operStack.push(ch);
                } else {
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        // 把运算的结果入数栈
                        numStack.push(res);
                        // 将操作符入符号栈
                        operStack.push(ch);

                    } else {
                        // 如果当前的操作符优先级大于栈中的操作符，就直接入栈
                        operStack.push(ch);
                    }
                }
            } else {
                // 如果发现是数，不能直接入栈，再向后看一位，如果是符号则可以入栈，否则继续扫描
                // numStack.push(ch - 48);

                keepNum += ch;

                if (index == expression.length() - 1) {
                    numStack.push(Integer.parseInt(keepNum));
                } else if (operStack.isOper(expression.charAt(index + 1))) {
                    numStack.push(Integer.parseInt(keepNum));
                    keepNum = StringConstants.EMPTY;
                }
            }
        }

        while (!operStack.isEmpty()) {
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            numStack.push(res);
        }

        return res;
    }

}

class CalculatorStack extends ArrayStack {

    public CalculatorStack(int capacity) {
        super(capacity);
    }

    /**
     * 判断优先级
     *
     * @param oper
     * @return
     */
    public static int priority(int oper) {
        if (oper == CharConstants.MULTIPLY || oper == CharConstants.DIVIDE) {
            return 1;
        } else if (oper == CharConstants.PLUS || oper == CharConstants.MINUS) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 是否为操作符
     *
     * @param val
     * @return
     */
    public static boolean isOper(char val) {
        return val == CharConstants.MULTIPLY || val == CharConstants.DIVIDE || val == CharConstants.PLUS || val == CharConstants.MINUS;

    }

    public static int cal(int num1, int num2, int oper) {
        int res = 0;

        switch (oper) {
            case CharConstants.PLUS:
                res = num1 + num2;
                break;
            case CharConstants.MINUS:
                res = num2 - num1; // 注意顺序
                break;
            case CharConstants.MULTIPLY:
                res = num1 * num2;
                break;
            case CharConstants.DIVIDE:
                res = num2 / num1;
                break;
            default:
                break;
        }

        return res;
    }
}