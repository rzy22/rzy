package com.rzy.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 资源类
class MyCache{

    private volatile Map<String,Object> map = new HashMap<>();
    // 传统的 lock 不足以满足我们的需求，一致性等到保证，并发性能落后
    //private Lock lock = new ReentrantLock();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * hash重要的是key，不是value，value可以是任何东西
     * key是主键，有主键有一切
     * @param key
     * @param value
     */
    public void put(String key,Object value){

        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成：");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){

        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取：");
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }
   }

   /* // 清除缓存
   public void clearMap(){
        map.clear();
   }*/

}

public class ReadWriteLockDemo {

    /**
     * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行
     * 但是如果有一个线程想去写共享资源，就不应该再有其他线程可以该线程进行读或写
     * 读读能共存
     * 读写不能共存
     * 写写不能共存
     *
     * 写操作：原子（完整一致） + 独占,中间的过程必须是一个完成的统一体，中间不许被分割不许被打断
     */

    public static void main(String[] args) {

        // 线程操作资源类
        MyCache myCache = new MyCache();

        // 五个线程写
        for (int i = 1; i <= 5; i++){
            final int tempInt = i;
            new Thread(() ->{
                myCache.put(tempInt + "",tempInt + "");
            },String.valueOf(i)).start();
        }

        // 五个线程读
        for (int i = 1; i <= 5; i++){
            final int tempInt = i;
            new Thread(() ->{
                myCache.get(tempInt + "");
            },String.valueOf(i)).start();
        }
    }
}
