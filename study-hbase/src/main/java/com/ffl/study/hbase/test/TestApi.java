package com.ffl.study.hbase.test;

import com.google.common.collect.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

/**
 * @author lff
 * @datetime 2020/07/26 15:10
 */
public class TestApi {

    private static Connection connection = null;

    private static Admin admin = null;

    private static final String TABLE_NAME = "test:stu";


    static {
        try {
            // 1.获取配置文件
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.quorum", "localhost");
            // configuration.set("zookeeper.znode.parent", "/hbase");

            // 2.获取管理员对象
            connection = ConnectionFactory.createConnection(configuration);


            // 3.admin对象
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // public static void main(String[] args) throws IOException {
    //     System.out.println(isTableExists("stu"));
    //
    //     // 1.创建表空间
    //     createNameSpace("test");
    //
    //     // 2.创建表
    //     createTable(TABLE_NAME, "info", "info1");
    //     // tus
    //
    //     // 3.创建表是否存在
    //     System.out.println(isTableExists(TABLE_NAME));
    //
    //     // 4.写入数据
    //     putData(TABLE_NAME, "1001", "info", "name", "张三");
    //
    //     // 5.查询数据
    //     get(TABLE_NAME, "1001", "info", "name");
    //
    //     // 6.扫描数据
    //     scanTable(TABLE_NAME);
    //
    //     // 7.删除数据
    //     deleteData(TABLE_NAME, "1001", "info", "sex");
    //
    //     // 8.删除表
    //     dropTable(TABLE_NAME);
    //
    //     close();
    // }

    public static void main(String[] args) throws IOException {
        // createNameSpace("test");
        // createTable("stu", "info", "info1");
        // get("stu", "1001", "info", "name");

        Table table = connection.getTable(TableName.valueOf("stu"));

        // putData("stu","123","info","col1","value1");
        // putData("stu","456","info","col1","value2");


        List<String> rowKeyList = Lists.newArrayList("123","456","abc");
        List<String> strings = get(table, rowKeyList, "info", "col1");

        System.out.println(strings.toString());

    }

    public static List<String> get(Table table, List<String> rowKeyList, String cf, String cn) throws IOException {
        List<String> resList = Lists.newArrayList();

        List<Get> getList = Lists.newArrayList();
        for (String rowKey : rowKeyList) {
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn));
            getList.add(get);
        }

        Result[] results = table.get(getList);

        for (Result result : results) {
            // 解析result并打印
            for (Cell cell : result.rawCells()) {
                System.out.println(Bytes.toString(CellUtil.copyRow(cell)));
                resList.add(Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }


        return resList;
    }


    /**
     * 表是否存在
     *
     * @param tableName 表名
     * @return
     */
    public static boolean isTableExists(String tableName) throws IOException {
        return admin.tableExists(TableName.valueOf(tableName));
    }

    /**
     * 创建表
     *
     * @param tabName
     * @param cfs
     * @throws IOException
     */
    public static void createTable(String tabName, String... cfs) throws IOException {

        // 判断列族是否存在
        if (cfs.length <= 0) {
            System.out.println("请设置列族信息");
            return;
        }

        // 判断表是否存在
        if (isTableExists(tabName)) {
            System.out.println(tabName + "表已存在！");
            return;
        }

        // 创建表描述器
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tabName));

        for (String cf : cfs) {
            // 添加列族信息
            hTableDescriptor.addFamily(new HColumnDescriptor(cf));
        }

        // 创建表
        admin.createTable(hTableDescriptor);
    }

    /**
     * 删除表
     *
     * @param tableName
     * @throws IOException
     */
    public static void dropTable(String tableName) throws IOException {
        if (!isTableExists(tableName)) {
            System.out.println("表" + tableName + "不存在！");
            return;
        }

        admin.disableTable(TableName.valueOf(tableName));
        admin.deleteTable(TableName.valueOf(tableName));
    }


    /**
     * 创建表空间
     *
     * @param ns
     */
    public static void createNameSpace(String ns) {
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(ns).build();

        try {
            admin.createNamespace(namespaceDescriptor);
        } catch (NamespaceExistException e) {
            System.out.println("表空间已存在！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存入数据
     *
     * @param tableName
     * @param rowKey
     * @param cf
     * @param cn
     * @param value
     * @throws IOException
     */
    public static void putData(String tableName, String rowKey, String cf, String cn, String value) throws IOException {

        // 获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));

        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn), Bytes.toBytes(value));

        table.put(put);

        table.close();
    }

    public static void get(String tableName, String rowKey, String cf, String cn) throws IOException {
        // 获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn));

        Result result = table.get(get);

        // 解析result并打印
        for (Cell cell : result.rawCells()) {
            // 打印数据
            System.out.println("CF:" + Bytes.toString(CellUtil.cloneFamily(cell))
                    + ", CN:" + Bytes.toString(CellUtil.cloneFamily(cell))
                    + ", Value:" + Bytes.toString(CellUtil.cloneValue(cell))

            );
        }

        table.close();
    }

    /**
     * 扫描表
     *
     * @param tableName
     * @throws IOException
     */
    public static void scanTable(String tableName) throws IOException {
        // 创建表对象
        Table table = connection.getTable(TableName.valueOf(tableName));

        // 构建scan对象
        // Scan scan = new Scan();

        Scan scan = new Scan(Bytes.toBytes("1001"), Bytes.toBytes("1003")); // 左闭右开

        // 扫描表
        ResultScanner resultScanner = table.getScanner(scan);

        for (Result result : resultScanner) {
            // 解析result并打印
            for (Cell cell : result.rawCells()) {
                // 打印数据
                System.out.println(
                        "RK:" + Bytes.toString(CellUtil.cloneRow(cell))
                                + ", CF:" + Bytes.toString(CellUtil.cloneFamily(cell))
                                + ", CN:" + Bytes.toString(CellUtil.cloneFamily(cell))
                                + ", Value:" + Bytes.toString(CellUtil.cloneValue(cell))

                );
            }
        }

        // 关闭表连接
        table.close();
    }


    public static void deleteData(String tableName, String rowKey, String cf, String cn) throws IOException {
        // 得到table
        Table table = connection.getTable(TableName.valueOf(tableName));

        // 构建delete对象  如果只有此设置 删除所有的列族
        Delete delete = new Delete(Bytes.toBytes(rowKey));

        // 设置删除的列
        // 删除所有版本
        delete.addColumns(Bytes.toBytes(cf), Bytes.toBytes(cn));

        // 删除一个版本，慎用，否则会出现诈尸
        // 删除标记分为 Delete/按rowkey删 、DeleteColumn/按列删、 DeleteFamily/按列族删除   Delete标记表示只删除指定时间戳数据
        delete.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn));

        // 删除列族
        delete.addFamily(Bytes.toBytes(cf));

        // 删除
        table.delete(delete);

        // 关闭连接
        table.close();
    }

    private static void close() {
        if (admin != null) {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}