package com.jessen.demo;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture
 */
public class ThreadDemo4 {
    public static void main(String[] args) throws Exception {
        // 异步执行 下面方法
        Integer result1 = CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            int result = sum(); //这是得到的返回值

            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);

            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

            return result;
        }).join();
        System.out.println(result1);


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
