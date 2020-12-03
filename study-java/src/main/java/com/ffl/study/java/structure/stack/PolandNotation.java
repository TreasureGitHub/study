package com.ffl.study.java.structure.stack;

import com.ffl.study.common.constants.StringConstants;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author lff
 * @datetime 2020/12/03 07:52
 */
public class PolandNotation {

    public static void main(String[] args) {
        // 定义逆波兰表达式
        // (3+4)*5-6 => 3 4 + 5 * 6 -
        String suffixExpression = "3 4 + 5 * 6 - ";
        String suffixExpression1 = "4 5 * 8 - 60 + 8 2 / +";
        // 4 * 5 - 8 + 60 + 8 / 2 => 4 5 * 8 - 60 + 8 2 / +

        // 思路
        // 1.先将 3 4 + 5 * 6 -  放入ArrayList
        // 2.将 ArrayList 传递给一个方法，配合栈 完成计算

        // System.out.println(calculate(suffixExpression1));

        System.out.println(calculate("1+((2+3)*4)-5"));

    }

    /**
     * 输入重罪表达式进行计算
     *
     * @param suffixExpression 中缀表达式
     * @return
     */
    public static int calculate(String suffixExpression) {
        // 1.将中缀表达式 转化成list
        List<String> infixList = toInfixExpressionList(suffixExpression);
        // 2.list转成后缀表达式
        List<String> suffixList = parseSuffixExpressionList(infixList);
        System.out.println(suffixList);
        // 3.计算
        return calculateFromList(suffixList);
    }

    /**
     * 将list类容器转为后缀表达式
     *
     * @param list
     * @return
     */
    private static List<String> parseSuffixExpressionList(List<String> list) {
        // 定义两个栈
        Stack<String> s1 = new Stack<>();
        // 因为s2栈转换过程中，没有pop操作，而且后续需要逆序太麻烦，因此用list代替
        ArrayList<String> s2 = Lists.newArrayList();

        for (String item : list) {
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equals(StringConstants.LEFT_PARENTHESE)) {
                s1.push(item);
            } else if (item.equals(StringConstants.RIGHT_PARENTHESE)) {
                // 如果是右括号，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号位置，此时这一对括号丢弃
                while (!s1.peek().equals(StringConstants.LEFT_PARENTHESE)) {
                    s2.add(s1.pop());
                }
                s1.pop(); // 消除小括号
            } else {
                // 当item的优先级小于等于栈顶运算符，将s1栈顶的运算符弹出并加入到s2中，再次转到4.1中新的栈顶运算符相比较
                while (s1.size() != 0 && getPriority(s1.peek()) >= getPriority(item)) {
                    s2.add(s1.pop());
                }
                // 还需要将item压入栈
                s1.push(item);
            }
        }

        // 将s1中的数据加入s2中
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }


        return s2;
    }

    /**
     * 传入后缀表达式链表得到 结果
     *
     * @param list
     * @return
     */
    private static int calculateFromList(List<String> list) {
        Stack<String> stack = new Stack<>();

        for (String item : list) {
            if (item.matches("\\d+")) { // 匹配多位数
                stack.push(item);
            } else {
                // pop出两个数，并计算,再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals(StringConstants.PLUS)) {
                    res = num1 + num2;
                } else if (item.equals(StringConstants.MINUS)) {
                    res = num1 - num2;
                } else if (item.equals(StringConstants.MULTIPLY)) {
                    res = num1 * num2;
                } else if (item.equals(StringConstants.DIVIDE)) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误！");
                }
                stack.push("" + res);
            }
        }

        return Integer.parseInt(stack.pop());
    }

    /**
     * 将中缀表达式转换成后缀表达式
     * <p>
     * 1.因为直接对字符串操作不方便，因此先将字符串转成中缀表达式 List
     * 2.expression "1+((2+3)*4)-5"
     *
     * @param expression
     * @return
     */
    private static List<String> toInfixExpressionList(String expression) {
        ArrayList<String> list = Lists.newArrayList();
        int i = 0;
        String str;
        char c;

        do {
            // 如果是非数字直接加入到list
            if ((c = expression.charAt(i)) < 48 || (c = expression.charAt(i)) > 57) {
                list.add("" + c);
                i++;
            } else { // 如果是数字 判断下一个是否为数字
                str = "";
                while (i < expression.length() && (c = expression.charAt(i)) >= 48 && (c = expression.charAt(i)) <= 57) {
                    str += c;
                    i++;
                }
                list.add(str);
            }

        } while (i < expression.length());

        return list;
    }

    /**
     * 得到运算符的优先级
     *
     * @param oper 运算符
     * @return
     */
    private static int getPriority(String oper) {
        if (StringConstants.MULTIPLY.equals(oper) || StringConstants.DIVIDE.equals(oper)) {
            return 2;
        } else if (StringConstants.PLUS.equals(oper) || StringConstants.MINUS.equals(oper)) {
            return 1;
        } else {
            return 0;
        }

    }

}