package com.rzy.countdownlatch_cyclicbarrier_semaphore;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier  做加法
 *      初始值为 0 ，加到指定的数值才开始做事
 * 案例：收集七颗龙珠才能召唤神龙
 *       7 个人，人到齐了才能开会
 */

public class CyclicBarrierDemo {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> {
            System.out.println("********************* 集齐龙珠，召唤神龙");
        });

        for (int i = 1; i <= 7 ; i++) {
            final int tempInt = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 收集到 " + tempInt + " 星龙珠");
                try {
                    // 收集到之后开始等待，全部收集完以后才能召唤神龙
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }
}
