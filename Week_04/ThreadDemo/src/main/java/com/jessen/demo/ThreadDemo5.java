package com.jessen.demo;

import java.util.concurrent.CompletableFuture;

/**
 * 无锁
 */
public class ThreadDemo5 {
    public static void main(String[] args) throws Exception {
        long start=System.currentTimeMillis();
        ThreadDemo5 threadDemo5 = new ThreadDemo5();
        new Thread(() -> {
            threadDemo5.sum();
        }).start();
        int result = threadDemo5.getValue();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private volatile Integer value = null;

    private void sum() {
        value = fibo(36);
    }

    private int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    private int getValue() {
        while (value == null) {

        }
        return value;
    }
}
