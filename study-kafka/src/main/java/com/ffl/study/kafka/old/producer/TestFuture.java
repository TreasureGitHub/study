package com.ffl.study.kafka.old.producer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author lff
 * @datetime 2020/06/11 21:44
 *
 * 测试 future
 *
 */
public class TestFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<?> future = executor.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("i = " + i);
                }
            }
        });

        future.get(); // 阻塞

        System.out.println("===================");

        executor.shutdown();

    }
}
