package com.rzy.error;

import java.util.ArrayList;
import java.util.List;

/**
 *  JVM 参数配置演示
 *  -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m （默认太大，方面演示设置的小点）
 *
 *
 *      GC 回收时间过长时会抛出 OutOfMemoryError，过长的定义是，超过 98% 的时间都用来做 GC ，并且回收了
 *      不到 2% 的堆内存，连续多次 GC 都只回收了 不到 2% 的情况下才会抛出。
 *
 *      假如不抛出 GC overhead limit 错误，会发生什么情况？
 *          就是 GC 清理的这么店内存很快会再次填满，迫使 GC 再次执行，这个就形成恶性循环，
 *          CPU 的使用率一直是 100%，而 GC 却没有任何成果。
 *
 */

public class GCOverheadDemo {

    public static void main(String[] args) {

        int i = 0;
        List<String> list = new ArrayList<>();

        try{
            while(true){
                list.add(String.valueOf(++i).intern());
            }
        }catch (Throwable e){
            System.out.println("***************" + i);
            e.printStackTrace();
            throw  e;
        }
    }
}
