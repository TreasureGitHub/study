package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lff
 * @datetime 2021/04/26 23:57
 */
public class MiddleValue {

    public static void main(String[] args) {

    }

    public static List<String> Perm(String str) {
        //基本情况，只有一个字符，则他的全排列是自身，结束递归
        if (str.length() == 1) return new ArrayList<>(Arrays.asList(str));

        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            //一个字符串分成左中右三部分，其中中间是要取的字符
            String left = str.substring(0, i);
            String center = str.substring(i, i + 1);
            String right = str.substring(i + 1, str.length());

            //求取出该字符后的全排列
            List<String> subList = Perm(left + right);
            subList.forEach(s -> {
                //进行合并，将取出的字符追加在前面
                list.add(center + s);
            });
        }
        //返回结果
        return list;
    }
}
