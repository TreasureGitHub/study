package com.ffl.study.flink.java;

import com.google.common.collect.Sets;
import org.apache.flink.api.common.functions.RichCoGroupFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import scala.Tuple2;

import java.util.Set;

import static com.ffl.study.common.constants.StringConstants.COMMA;

/**
 * @author lff
 * @datetime 2021/03/31 23:50
 */
public class JoinStateTest {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> ds1 = env.socketTextStream("localhost", 7000);
        SingleOutputStreamOperator<Tuple2<String, String>> stream2 = env.socketTextStream("localhost", 7001).flatMap(new MyFlatMapFunction());

        ds1
                .flatMap(new MyFlatMapFunction())
                .coGroup(stream2)
                .where(new KeySelector<Tuple2<String, String>, String>() {
                    @Override
                    public String getKey(Tuple2<String, String> value) throws Exception {
                        return value._1;
                    }
                })
                .equalTo(new KeySelector<Tuple2<String, String>, String>() {
                    @Override
                    public String getKey(Tuple2<String, String> value) throws Exception {
                        return value._1;
                    }
                })
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                .apply(new RichCoGroupFunction<Tuple2<String, String>, Tuple2<String, String>, Object>() {

                    private MapState<String, Integer> mapState;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        MapStateDescriptor<String, Integer> mapStateDesc = new MapStateDescriptor<>("mapState", String.class, Integer.class);
                        mapState = getRuntimeContext().getMapState(mapStateDesc);
                    }

                    @Override
                    public void coGroup(Iterable<Tuple2<String, String>> first, Iterable<Tuple2<String, String>> second, Collector<Object> out) throws Exception {

                        System.out.println("-----------------------------------");

                        Set<String> set = Sets.newHashSet();
                        for (Tuple2<String, String> item : first) {
                            // byKey = item._1;
                            set.add(item._1);
                            mapState.put(item._2(), null);
                        }

                        for (Tuple2<String, String> item : second) {
                            // byKey = item._1;
                            set.add(item._1);
                            mapState.put(item._2(), null);
                        }

                        int cnt = 0;
                        for (String key : mapState.keys()) {
                            cnt++;
                        }

                        System.out.print("key : " + set.toString() + "  value:");
                        for (String key : mapState.keys()) {
                            System.out.print(key + ",");
                        }

                        if (cnt != 0) {
                            // out.collect(new Tuple2<>(byKey, cnt));
                        }

                        System.out.println("-----------------------------------");

                    }
                })
                .print();

        env.execute(JoinStateTest.class.getSimpleName());
    }

    private static class MyFlatMapFunction extends RichFlatMapFunction<String, Tuple2<String, String>> {

        private MapState<String, Integer> mapState;

        @Override
        public void open(Configuration parameters) throws Exception {
            MapStateDescriptor<String, Integer> mapStateDesc = new MapStateDescriptor<>("mapState1", String.class, Integer.class);
            mapState = getRuntimeContext().getMapState(mapStateDesc);
        }

        @Override
        public void flatMap(String value, Collector<Tuple2<String, String>> out) throws Exception {
            String[] split = value.split(COMMA);
            out.collect(new Tuple2<>(split[0], split[1]));
        }
    }
}
