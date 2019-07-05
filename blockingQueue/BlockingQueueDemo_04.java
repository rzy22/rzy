package com.rzy.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *  超时退出
 */

public class BlockingQueueDemo_04 {

    public static void main(String[] args) throws InterruptedException {

        // List list = new ArrayList();
        // ArrayList 默认初始值为 10，可以不同填
        // ArrayBlockingQueue 一定要填初始值，告诉阻塞队列有界是多大
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);

        // 生产者，成功返回 true
        System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c",2L, TimeUnit.SECONDS));
        // 当队列满的时候，队列会阻塞生产者一定的时间，超过设置的时间生产者线程会退出，返回 false
        //System.out.println(blockingQueue.offer("x",2L, TimeUnit.SECONDS));

        // 消费者，返回消费的元素值
        System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));
        // 当队列为空时，队列会阻塞消费者一定时间，超过设置的时间，消费者线程会退出，返回 null
        //System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));

    }
}
