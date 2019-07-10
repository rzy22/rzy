package com.rzy.error;

import java.nio.ByteBuffer;

/**
 *      配置参数：堆内存10m
 *          -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 *      故障现象：
 *          java.lang.OutofMemory: Direct buffer memory   内存崩溃，直接内存溢出
 *      导致原因：
 *          写 NIO 程序经常使用 ByteBuffer 来读取或者写入数据，这是一种基于通道（Channel）与缓冲区（Buffer）的I/O 方式
 *          它是可以使用 Native 函数库直接分配堆外内存，然后通过一个存储在 Java 堆里面的 DirectByteBuffer 对象作为
 *          这块内存的应用进行操作
 *
 *          ByteBuffer.allocate(capability) 第一种方式是分配 JVM 堆内存，属于 GC 管辖范围，由于需要拷贝所以速度相对较慢
 *
 *          ByteBuffer.allocateDirect(capability) 第二种方式是分配 OS 本地内存，不属于 GC 管辖范围，由于不需要内存拷贝所以速度相对较快
 *
 *          但是如果不断分配本地内存，堆内存很少使用，那么 JVM 就不需要执行 GC，DirectByteBuffer 对象们就不会被回收
 *          这时候堆内存充足，但是本地内存可能已经使用光了，再次尝试分配本地内存就会出现 OutofMemoryError，那程序就直接崩溃了
 */

public class DirectBufferMemoryDemo {

    public static void main(String[] args) {

        // 初始配置的最大直接内存，我本地内存是 8G，看 Java 用了多少
        System.out.println("配置的maxDirectMemory:" + (sun.misc.VM.maxDirectMemory() / (double)1024 / 1024) + "MB");

        try{ Thread.sleep(3000);} catch (InterruptedException e){ e.printStackTrace();}

        // -XX:MaxDirectMemorySize=5m  我们配置为 5 MB，但实际使用 6 MB
        ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
