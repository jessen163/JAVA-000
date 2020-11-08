package com.jessen.demo;

import java.util.concurrent.Callable;

public class ThreadDemo1 {
    public static void main(String[] args) throws Exception {
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Callable<Integer> callable = new Callable<Integer>() {
            public Integer call() throws Exception {
                long start=System.currentTimeMillis();
                int result = sum(); //这是得到的返回值

                // 确保  拿到result 并输出
                System.out.println("异步计算结果为："+result);

                System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

                return result;
            }
        };
        System.out.println(callable.call());


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
