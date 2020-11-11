package com.jessen.demo;

import java.util.concurrent.Semaphore;

/**
 * Semaphore
 */
public class ThreadDemo7 {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ThreadDemo7 threadDemo7 = new ThreadDemo7();
        new Thread(() -> {
            try {
                threadDemo7.sum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(2000);
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + threadDemo7.getValue());

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private volatile Integer value = null;
    private final Semaphore semaphore = new Semaphore(1);

    private void sum() throws InterruptedException {
        semaphore.acquire();
        value = fibo(36);
        semaphore.release();
    }

    private int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    private int getValue() throws InterruptedException {
        int result = 0;
        semaphore.acquire();
        result = this.value;
        semaphore.release();
        return result;
    }
}
