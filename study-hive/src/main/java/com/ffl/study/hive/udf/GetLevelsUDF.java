package com.ffl.study.hive.udf;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author lff
 * @datetime 2020/05/24 22:02
 * <p>
 * 查询层次结构，输入source、分隔符、start、end
 * end可以不输入，如果不输入，则表示从start截取到最后
 * <p>
 * <p>
 * eg:get_levels('A/B/C/D','/',1,3) -> 'A/B/C'
 * get_levels('A/B/C/D','/',2)   -> 'A/B/C/D'
 */
public class GetLevelsUDF extends UDF {

    /**
     * source，为源数据，sep为分割字符串，start为起始位置，从1开始 end为结束位置
     *
     * @param source
     * @param sep
     * @param start
     * @param end
     * @return
     */
    public String evaluate(String source, String sep, int start, int end) {
        if (source == null || sep == null) {
            return null;
        }

        String[] arr = source.split(sep);
        return StringUtils.join(ArrayUtils.subarray(arr, start - 1, end), sep);
    }


    /**
     * 结束位置为最末尾
     *
     * @param source
     * @param sep
     * @param start
     * @return
     */
    public String evaluate(String source, String sep, int start) {
        if (source == null || sep == null) {
            return null;
        }
        String[] arr = source.split(sep);
        return StringUtils.join(ArrayUtils.subarray(arr, start - 1, arr.length), sep);
    }

    /**
     * 默认以"/" 为分割
     *
     * @param source
     * @param start
     * @param end
     * @return
     */
    public String evaluate(String source, int start, int end) {
        return evaluate(source, "/", start, end);
    }


    /**
     * 默认以"/" 为空格
     *
     * @param source
     * @param start
     * @return
     */
    public String evaluate(String source, int start) {
        return evaluate(source, "/", start);
    }
}
