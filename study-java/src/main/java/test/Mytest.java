package test;

/**
 * @author lff
 * @datetime 2021/01/21 20:35
 */
public class Mytest {

    public static void main(String[] args) {
        String strs = "i am programmer";
        System.out.println(reverseLine(strs, " "));
    }

    /**
     * 翻转 字符串
     *
     * @param source 待处理字符串
     * @param sep    分隔符
     * @return
     */
    public static String reverseLine(String source, String sep) {
        if (source == null || sep == null) {
            return null;
        }

        String[] arr = source.split(sep);

        for (int i = 0; i < arr.length; i++) {
            arr[i] = reverseWord2(arr[i]);
        }

        return String.join(sep, arr);
    }

    /**
     * 翻转单个字符串
     *
     * @param str
     * @return
     */
    public static String reverseWord(String str) {
        if (str == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();

        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }

        return sb.toString();
    }

    /**
     * 翻转单个字符串
     *
     * @param str
     * @return
     */
    public static String reverseWord1(String str) {
        if (str == null) {
            return str;
        }

        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 翻转单个字符串
     *
     * @param str
     * @return
     */
    public static String reverseWord2(String str) {
        if (str == null || str.length() == 1) {
            return str;
        }

        return reverseWord2(str.substring(1)) + str.charAt(0);
    }
}
