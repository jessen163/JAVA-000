package com.jessen.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * join
 */
public class ThreadDemo6 {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        AtomicInteger result = new AtomicInteger();
        Thread thread = new Thread(()->{
            result.set(sum());
        });
        thread.start();
        thread.join();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result.get());

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }
}
