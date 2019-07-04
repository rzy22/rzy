package com.rzy.juc_01;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 *      构造默认有一个参数，没有默认值，必须传入一个计数
 *      （类似于倒计时）
 *      CountDownLatch 是做减法
 */

public class CountDownLatchDemo {

    public static void main(String[] args) throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i<= 6; i++){
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 国，被灭");
                // 每走一个就减一下
                countDownLatch.countDown();
            },CountryEnum.forEach_countryEnum(i).getRetMessage()).start();
        }

        // 在countDownLatch.countDown();没有减到 0 之前都是一个等待的状态，减到 0 之后自动解除，开始 main 线程
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t 秦国，一统天下");
    }

    public static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i<= 6; i++){
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 我的事情做完了，我走了");
                // 每走一个就减一下
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        // 在countDownLatch.countDown();没有减到 0 之前都是一个等待的状态，减到 0 之后自动解除，开始 main 线程
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t 好，都走完了，该我走了");
    }
}
