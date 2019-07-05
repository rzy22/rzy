package com.rzy.countdownlatch_cyclicbarrier_semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *  Semaphore   信号灯，可伸缩，有加有减
 *              CountDownLatch 只能 -- ，减到 0 才开工
 *              CyclicBarrier 只能 ++ ，+ 到指定的数值才开工
 *              多个线程抢占多个资源（synchronized 和 lock 都是多个线程抢占一个资源）
 *  案例：6 个车抢占三个车位 （线程就是车）
 *              semaphore.acquire();  抢占
 *              semaphore.release();  释放
 */

public class SemaphoreDemo {

    public static void main(String[] args) {

        // 模拟三个车位
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 6 ; i++) {
            new Thread(() -> {
                try {
                    // 某一个线程抢到了这个车位
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 抢到车位");
                    try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e){ e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName() + "\t 停车 3 秒后，离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    // 释放，车停在车位上办完事了，开车走了，释放当前的车位
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
