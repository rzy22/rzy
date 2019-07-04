package com.rzy.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁：好处是不用阻塞，坏处就是循环重了以后，CPU会有性能消耗
 *
 * 例子：
 *      电话是公共资源
 *      现在你在打电话，突然有个人过来也想打电话
 *      第一种情况：这个人就等着你打完电话，才有机会用电话，现在这个人就属于 wait 状态，阻塞状态，什么都不干，就等着你打完电话
 *      第二种情况：这个人发现你在打电话，你要打很长时间，他就去干别的事情，过两分钟又回来看看你打完了没有，没有打完，就继续
 *                  去做自己的事情，再过一会，再看看，你打完了他用，他在等待这个过程就叫自旋，他不会一直等着。
 *
 *      自旋的本质就是 while + CAS思想
 */

public class SpinLockDemo {

    // 原子引用线程   （引用型默认为null）
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    // Lock
    public void  myLcok(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in");
        /**
         * 期望值是 null，真实值也是 null，就把自己放进去
         * （好比打电话，你用的时候不希望别人正在用，正好没人在用的时候你直接拿上就开始用）
         */
        while(!atomicReference.compareAndSet(null,thread)){

        }
    }

    // unLock
    private void myUnlock(){
        Thread thread = Thread.currentThread();
        /**
         * 你用完了，就把这个又变回 null
         * （好比打电话，你打完了，用完电话了，你要把电话放在那里，然后下一个人用）
         */
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName() + "\t invoked myUnlock()");
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {

        // 线程调用资源类
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() ->{
            spinLockDemo.myLcok();
            // 暂停一会线程
            try{ TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.myUnlock();
        },"AA").start();


        // main 睡一会
        try{ TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() ->{
            spinLockDemo.myLcok();
            // 为了效果
            try{ TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.myUnlock();
        },"BB").start();

    }

}
