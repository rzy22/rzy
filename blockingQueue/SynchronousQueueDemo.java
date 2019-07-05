package com.rzy.blockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue
 *      没有容量，和其他 Blocking 不一样，它是不存储元素的，每一个 put 必须要等待一个
 *      take 操作，否则不能继续添加元素，消费者也一样，队列里面没有东西，就不能消费
 *      来一个，拿一个，这样的一个操作
 */

public class SynchronousQueueDemo {

    public static void main(String[] args) {

        // 默认是一个非公平锁
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put a");
                blockingQueue.put("a");

                System.out.println(Thread.currentThread().getName() + "\t put b");
                blockingQueue.put("b");

                System.out.println(Thread.currentThread().getName() + "\t put c");
                blockingQueue.put("c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA").start();


        new Thread(() -> {
            try {
                // 方便测试效果更明显
                try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
                // 取
                System.out.println(blockingQueue.take());

                // 方便测试效果更明显
                try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
                // 取
                System.out.println(blockingQueue.take());

                // 方便测试效果更明显
                try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
                // 取
                System.out.println(blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB").start();

    }
}
