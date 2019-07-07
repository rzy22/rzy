package com.rzy.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *      一池多线程
 */

public class NewCachedThreadPoolDemo {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newCachedThreadPool();

        // 模拟 10 个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{
            // 10 个用户来办理业务
            for (int i = 1; i <= 10 ; i++) {
                // 10 个用户的请求都由多个处理线程来办理
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                // 为了说明在顾得过来的情况下，注释掉下面这句话就是多个线程一起处理
                try{ TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e){ e.printStackTrace();}
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
