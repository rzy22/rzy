package com.rzy.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *      一池一线程
 */

public class NewSingleThreadExecutorDemo {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        // 模拟 10 个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{
            // 10 个用户来办理业务
            for (int i = 1; i <= 10 ; i++) {
                // 10 个用户的请求都由这 1 个线程池来办理
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
