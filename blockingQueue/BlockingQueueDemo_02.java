package com.rzy.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *  特殊值组（布尔值组）
 */

public class BlockingQueueDemo_02 {

    public static void main(String[] args) {

        // List list = new ArrayList();
        // ArrayList 默认初始值为 10，可以不同填
        // ArrayBlockingQueue 一定要填初始值，告诉阻塞队列有界是多大
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        // 失败 false ，成功 true
        //System.out.println(blockingQueue.offer("x"));

        // 检查方法，检查队列是否是空，不是空就显示队列的第一个，也就是 a
        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        // 成功返回移除的元素，队列没有返回 null
        //System.out.println(blockingQueue.poll());

    }
}
