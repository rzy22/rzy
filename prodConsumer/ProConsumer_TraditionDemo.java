package com.rzy.prodConsumer;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 资源类
class ShareData{

    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception{
        lock.lock();
        try{
            // 1.判断
            while (number != 0){
                // 等待，不能生产
                condition.await();
            }
            // 2.干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            // 3.通知唤醒
            condition.signalAll();
        } catch (Exception e){

        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws Exception{
        lock.lock();
        try{
            // 1.判断
            while (number == 0){
                // 等待，不能生产
                condition.await();
            }
            // 2.干活
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            // 3.通知唤醒
            condition.signalAll();
        } catch (Exception e){

        } finally {
            lock.unlock();
        }
    }
}

/**
 * 传统版消费者和生产者
 *      一个初始值为 0 的变量，两个线程对其进行交替操作，一个加 1 ，一个减 1，来五轮
 *
 *      1.线程、操纵（方法）、资源类
 *      2.判断、干活、通知
 *      3.防止虚假唤醒机制
 */

public class ProConsumer_TraditionDemo {

    public static void main(String[] args) {

        ShareData shareData = new ShareData();

        // AA 线程生产
        new Thread(() -> {
            for (int i = 1; i <= 5 ; i++) {
                try{
                    shareData.increment();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"AA").start();

        // BB 线程消费
        new Thread(() -> {
            for (int i = 1; i <= 5 ; i++) {
                try{
                    shareData.decrement();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"BB").start();
    }
}
