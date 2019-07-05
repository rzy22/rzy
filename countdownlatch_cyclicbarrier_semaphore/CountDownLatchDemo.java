package com.rzy.countdownlatch_cyclicbarrier_semaphore;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch  做减法
 *      给一个初始值，减到 0 之后开工
 * 案例：
 *      7 位同学在班级里上自习，一个一个的走，因为有的同学学完了，有的还没有学完
 *      班长必须等 7 位同学都走完了，才能关灯关门走人，要不然会有人别锁在教室里面
 *      等全部走完了，班长关灯关门走人
 */

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(7);

        for (int i = 1; i <= 7 ; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 上完自习走了");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t 都走完了，班长关灯关门走人");
    }
}
