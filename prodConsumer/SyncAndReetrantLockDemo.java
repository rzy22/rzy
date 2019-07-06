package com.rzy.prodConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 结合锁绑定多个条件 Condition
 *      synchornized 没有
 *      ReentrantLock 用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像 synchornized 要么唤醒一个，要么全部唤醒
 *
 *
 * 题目：
 *      多个线程之间按顺序调用，实现 A -> B -> C 三个线程的启动，要求如下
 *      AA 打印 5 次，BB 打印十次，CC 打印 15 次
 *      紧接着
 *      AA 打印 5 次，BB 打印十次，CC 打印 15 次
 *      ......
 *      来 5 轮
 *
 *      实现到底是哪个线程被唤醒，大大加强调用的维度和精度，精确唤醒
 */

class ShareResource{

    // A:1 B:2 C:3
    private int number = 1;
    // 一把锁，这把锁绑定了 3 个 Condition，可以精确唤醒
    private Lock lock = new ReentrantLock();
    // 三把备用钥匙
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    // 三个方法可以放在一起，但是为了更好的理解，分开写了
    // A 线程
    public void prints5(){
        lock.lock();
        try{
            // 1. 判断
            // 1 不等于 1，A 线程第一次进来要干活，打印 5 次
            while(number != 1){
                c1.await();
            }
            // 2. 干活
            for (int i = 0; i < 5 ; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 3. 通知
            // 多线程编程修改标志位非常重要
            number = 2;
            c2.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // B 线程
    public void prints10(){
        lock.lock();
        try{
            // 1. 判断
            // 2 不等于 2，B 线程第一次进来要干活，打印 10 次
            while(number != 2){
                c2.await();
            }
            // 2. 干活
            for (int i = 0; i < 10 ; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 3. 通知
            // 多线程编程修改标志位非常重要
            number = 3;
            c3.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // C 线程
    public void prints15(){
        lock.lock();
        try{
            // 1. 判断
            // 3 不等于 3，C 线程第一次进来要干活，打印 15 次
            while(number != 3){
                c3.await();
            }
            // 2. 干活
            for (int i = 0; i < 15 ; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 3. 通知
            // 多线程编程修改标志位非常重要
            number = 1;
            // 继续又回到 A 线程
            c1.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SyncAndReetrantLockDemo {

    public static void main(String[] args) {

        ShareResource shareResource = new ShareResource();

        // A 线程
        new Thread(() -> {
            for (int i = 0; i < 5 ; i++) {
                shareResource.prints5();
            }
        },"A").start();

        // B 线程
        new Thread(() -> {
            for (int i = 0; i < 5 ; i++) {
                shareResource.prints10();
            }
        },"B").start();

        // C 线程
        new Thread(() -> {
            for (int i = 0; i < 5 ; i++) {
                shareResource.prints15();
            }
        },"C").start();
    }
}
