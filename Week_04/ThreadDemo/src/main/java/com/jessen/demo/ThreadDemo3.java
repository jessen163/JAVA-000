package com.jessen.demo;

import java.util.concurrent.*;

/**
 * FutureTask方式
 */
public class ThreadDemo3 {
    public static void main(String[] args) throws Exception {
        // 异步执行 下面方法
        FutureTask<Integer> result = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                long start=System.currentTimeMillis();
                int result = sum(); //这是得到的返回值

                // 确保  拿到result 并输出
                System.out.println("异步计算结果为："+result);

                System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

                return result;
            }
        });
        new Thread(result).start();
        System.out.println(result.get());


        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
