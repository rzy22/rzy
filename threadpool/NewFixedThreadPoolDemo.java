package com.rzy.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *      固定线程数的池子
 *      线程池的处理线程可以复用
 *
 *      场景：
 *          银行办理业务，银行就是池子，5 个窗口就是处理线程
 *
 *          运行出来的结果可能有第 6 个线程，因为池子里面就只有 5 个，固定的，
 *          就像银行里面只有 5 个窗口，不可能有第 6 个办理业务的窗口，建的时候是几个就是几个
 */

public class NewFixedThreadPoolDemo {

    public static void main(String[] args) {

        // 一池 5 个处理线程
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        // 模拟 10 个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{
            // 10 个用户来办理业务
            // 不是 10/2，每个窗口办理两个业务，不确定，每个用户的业务不一样
            for (int i = 1; i <= 10 ; i++) {
                // 10 个用户的请求都由这 1 个线程池来办理，因为线程池中有 5 个处理线程
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
