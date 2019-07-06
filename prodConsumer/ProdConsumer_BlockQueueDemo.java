package com.rzy.prodConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *      volatile / CAS / atomicInteger / BlockQueue / 线程交互 / 原子引用
 */

// 资源类
class MyResource{

    // volatile 一旦改变，所有线程都可见
    private volatile boolean FLAG = true; // 默认开启，进行生产 + 消费（标志位）
    private AtomicInteger atomicInteger = new AtomicInteger(); // 默认是 0 （生产一个消费一个操作）
    BlockingQueue<String> blockingQueue = null;

    // 构造方法
    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    // 生产
    public void myProd() throws Exception{
        String data = null;
        boolean retValue;
        // 判断
        while(FLAG){
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data,2L, TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
            }else{
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 老板叫停了，表示 FLAG = false，生产动作结束");
    }

    // 消费
    public void myConsumer() throws Exception{
        String result = null;
        // 判断（FLAG 生产和消费是配对的，同生共死的那种）
        while(FLAG){
            // 两秒钟等不到就不取了
            result = blockingQueue.poll(2L,TimeUnit.SECONDS);
            // 如果取不到了
            if(null == result || result.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过 2 秒没有取到东西，消费退出");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列" + result + "成功");
        }
    }

    // main 线程叫停
    public void stop() throws Exception{
        this.FLAG = false;
    }
}

public class ProdConsumer_BlockQueueDemo {

    public static void main(String[] args) throws Exception {

        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(10));

        // 生产者线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产者线程启动");
            try {
                myResource.myProd();
            }catch (Exception e){
                e.printStackTrace();
            }
        },"Prod").start();

        // 消费者线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费者线程启动");
            // 只是为了测试好看清楚加的两行
            System.out.println();
            System.out.println();
            try {
                myResource.myConsumer();
                // 只是为了测试好看清楚加的两行
                System.out.println();
                System.out.println();
            }catch (Exception e){
                e.printStackTrace();
            }
        },"Consumer").start();

        // 停一会
        try {
            TimeUnit.SECONDS.sleep(5);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // 只是为了测试好看清楚加的两行
        System.out.println();
        System.out.println();
        System.out.println("\t 5 秒钟时间到，main 线程叫停");
        // 只是为了测试好看清楚加的两行
        System.out.println();
        System.out.println();
        myResource.stop();

    }
}
