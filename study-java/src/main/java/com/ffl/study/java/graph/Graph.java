package com.ffl.study.java.graph;

import com.ffl.study.common.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lff
 * @datetime 2020/06/30 22:37
 */
public class Graph {

    /**
     * 点集合
     */
    private List<String> vertexList;

    /**
     * 边
     */
    private int[][] edges;

    /**
     * 边的数目
     */
    private int numOfEdges;

    /**
     * 构造器
     *
     * @param n  点的个数
     */
    public Graph(int n) {
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
    }

    /**
     * 得到 顶点的数目
     *
     * @return
     */
    public int getNumOfVertex(){
        return vertexList.size();
    }

    /**
     * 返回 节点 i(下标) 对应的数据 0->"A" 1->"B" 2->"C"
     *
     * @return
     */
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    /**
     * 返回v1、v2的权值
     *
     * @param v1
     * @param v2
     * @return
     */
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }

    /**
     * 显示图对应的矩阵
     */
    public void showGraph(){
        ArrayUtils.println(edges);
    }

    /**
     * 得到边的树木
     *
     * @return
     */
    public int getNumOfEdges(){
        return numOfEdges;
    }

    /**
     * 插入节点
     *
     * @param vertex
     */
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     *
     * @param v1      第一个顶点对应的的下标 ，即第几个定点
     * @param v2      第二个顶点对应的下标
     * @param weight  权值
     */
    public void insertEdge(int v1,int v2,int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
    }
}
