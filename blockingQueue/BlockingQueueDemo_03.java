package com.rzy.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *  阻塞组
 */

public class BlockingQueueDemo_03 {

    public static void main(String[] args) throws InterruptedException {

        // List list = new ArrayList();
        // ArrayList 默认初始值为 10，可以不同填
        // ArrayBlockingQueue 一定要填初始值，告诉阻塞队列有界是多大
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);

        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        // 队列满的时候，会一直阻塞，不加进去不停止或者响应中断退出
        //blockingQueue.put("x");

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        // 当队列为空时，队列会一直阻塞消费者线程，知道队列可用（里面有元素）
        //blockingQueue.take();

    }
}
