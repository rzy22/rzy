package com.rzy.lock;

import java.util.concurrent.TimeUnit;

/**
 *  死锁：
 *      是指两个或者两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象
 *      若无外力干涉，那它们都将无法推进下去
 *
 *  解决办法：
 *      idea 的 Terminal 窗口（一般在左下角）在里面输入以下命令：
 *          jsp -l           回车，看最前面的进程号
 *          jstask 进程号    回车，就可以找到对应的信息，去相应的类中找到代码的逻辑错误
 */

// 资源类
class HoldLockThread implements Runnable{

    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockA + "\t 尝试获得：" + lockB);

            // 为了看效果
            try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }

            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockB + "\t 尝试获得：" + lockA);
            }
        }
    }
}

public class DeadLockDemo {

    public static void main(String[] args) {

        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA,lockB),"Thread AAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"Thread BBB").start();

        /**
         *  linux ps -eg|grep xxx
         *  windows 下的 java 运行程序，也有类似于 ps 的查看进程的命令，但是目前我们需要查看的只是 java
         */
    }
}
