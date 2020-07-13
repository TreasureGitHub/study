package com.ffl.study.java.graph;

/**
 * @author lff
 * @datetime 2020/06/30 22:37
 */
public class GraphTest {

    public static void main(String[] args) {
        int n = 5;
        String[] vertexValue = {"A", "B", "C", "D", "E"};

        Graph graph = new Graph(n);

        // 添加顶点
        for (String value:vertexValue){
            graph.insertVertex(value);
        }

        // 添加边
        // A-B A-C B-C B-D B-E
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);

        graph.showGraph();
    }
}
