package com.rzy.threadpool;

import java.util.concurrent.*;

/**
 *  手写线程池：7 参数的
 *          核心线程 2
 *          最大线程 5
 *          空闲线程过期时间 1
 *          单位为秒
 *          阻塞队列 3
 *          线程工厂
 *          拒绝策略使用默认
 *              CallerRunsPolicy：不会抛弃任务，也不会抛出异常，而是将某些任务回退到调用者，从而降低新任务的流量
 *              AbortPolicy（默认）：直接抛出 RejectedExecutionException 异常组织系统正常运行
 *              DiscardOldestPolicy：抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务
 *              DiscardPolicy：直接丢弃任务，不给予任何处理也不抛出异常，如果允许任务丢失，这是最好的一种方案
 */

public class MyThreadPool {

    public static void main(String[] args) {

        // 获取 CPU 核数
        System.out.println(Runtime.getRuntime().availableProcessors());

        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        try{
            // 模拟 20 个用户办理业务，每一个用户就是一个外部来的请求线程
            for (int i = 1; i <= 20; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 来办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
