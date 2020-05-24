package com.ffl.study.hive.udf;

import com.google.common.collect.Maps;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.List;
import java.util.Map;

/**
 * @author lff
 * @datetime 2020/05/24 22:42
 * <p>
 * eg：select array_to_map(array('zhangsan','18','90')); -> {"score":"90","name":"zhangsan","age":"18"}
 */
public class ArrayToMapGenericUDF extends GenericUDF {

    private ListObjectInspector listInspector;

    private MapObjectInspector mapInspector;

    /**
     * 输入参数判断
     *
     * @param arguments
     * @return
     * @throws UDFArgumentException
     */
    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 1) {
            throw new UDFArgumentException("Must have one parameter");
            //   检查传入参数是否为array
        } else if (!(arguments[0] instanceof StandardListObjectInspector)) {
            throw new UDFArgumentException("Must be array");
        }

        // ListObjectInspector  Array<String>
        listInspector = ObjectInspectorFactory.getStandardListObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        // MapObjectInspector Map<String,String>
        mapInspector = ObjectInspectorFactory.getStandardMapObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector, PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        // 返回类型为 Map<String,String>
        return mapInspector;
    }

    /**
     * 逻辑计算
     *
     * @param arguments
     * @return
     * @throws HiveException
     */
    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        Map<String, String> resMap = Maps.newHashMap();

        // listInspector 解析数据
        List<String> arr = (List<String>) listInspector.getList(arguments[0].get());

        resMap.put("name", arr.get(0));
        resMap.put("age", arr.get(1));
        resMap.put("score", arr.get(2));
        return resMap;
    }

    /**
     * 显示字符串
     *
     * @param children
     * @return
     */
    @Override
    public String getDisplayString(String[] children) {
        return "function ArrayToMap";
    }

}
