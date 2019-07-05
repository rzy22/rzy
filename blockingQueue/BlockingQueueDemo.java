package com.rzy.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *  异常组
 */

public class BlockingQueueDemo {

    public static void main(String[] args) {

        // List list = new ArrayList();
        // ArrayList 默认初始值为 10，可以不同填
        // ArrayBlockingQueue 一定要填初始值，告诉阻塞队列有界是多大
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        // add 方法，超出界直接报java.lang.IllegalStateException: Queue full  队列已满
        //System.out.println(blockingQueue.add("x"));

        // 检查方法，检查队列是否是空，不是空就显示队列的第一个，也就是 a
        System.out.println(blockingQueue.element());

        // 队列为空时，再使用 remove 方法，就直接抛 NoSuchElementExcept ,没有异常
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

    }
}
