package com.rzy.error;

/**
 *      部署都 linux 上运行
 *          普通用户登录到达限制就会报出 java.lang.OutOfMemoryError: unable to create new native thread
 *          root 用户登录可以一直 new（但是实际工作当中你也不是 root 用户）
 *
 *
 *      高并发请求服务器时，经常出现如下异常：java.lang.OutOfMemoryError: unable to create new native thread
 *      准确的讲该 native thread 异常和对应的平台有关
 *
 *      导致原因：
 *          1. 你的应用创建的线程太多了，一个应用进程创建多个线程，超过系统承载极限
 *          2. 你的服务器并不允许你的应用程序创建这么多的线程，linux 系统默认允许单个进程可以创建的线程数是 1024 个
 *             你的应用创建超过这个数量，就会报 java.lang.OutOfMemoryError: unable to create new native thread
 *
 *      解决办法:
 *          1. 想办法降低你应用程序创建线程的数量，分析用是否真的需要创建这么多线程，如果不是，改代码讲线程数降到最低
 *          2. 对于有的应用，确实需要创建很多线程，远超过 linux 系统的默认 1024 个线程的限制，可以通过修改 linux 服务器配置，扩大 linux 的默认限制
 */

public class UnableCreateNewThreadDemo {

    public static void main(String[] args) {

        // 没有一个跳出去的环境
        for (int i = 0;   ; i++) {
            System.out.println("******************* i = " + i);

            new Thread(() -> {
                try{ Thread.sleep(Integer.MAX_VALUE); } catch (InterruptedException e){ e.printStackTrace(); }
            },"" + i).start();
        }

    }
}
