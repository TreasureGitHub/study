package com.ffl.study.flink.java;

import com.ffl.study.common.constants.StringConstants;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author lff
 * @datetime 2021/03/13 18:58
 */
public class Test {

    private static final String CLS_NAME = Test.class.getSimpleName();

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        DataStreamSource<String> dataStream = env.socketTextStream("localhost", 7777);
        SingleOutputStreamOperator<Tuple2<String, Long>> filter = dataStream.
                map(new MapFunction<String, Tuple2<String, Long>>() {

                    @Override
                    public Tuple2<String, Long> map(String value) throws Exception {
                        String[] arr = value.split(StringConstants.COMMA);
                        return new Tuple2<String, Long>(arr[0], Long.valueOf(arr[1]));
                    }
                })
                .filter(new MyFilter());

        filter.print();


        env.execute(CLS_NAME);
    }

    private static class MyFilter extends RichFilterFunction<Tuple2<String, Long>> {

        private Long maxTimeStamp = 0L;
        private static int DELAY = 10;

        @Override
        public boolean filter(Tuple2<String, Long> value) throws Exception {
            if(maxTimeStamp < value.f1){
                maxTimeStamp = value.f1;
            }

            return maxTimeStamp < value.f1 + DELAY;
        }
    }
}
