package io.github.kimmking.gateway.filter;

public class ThreadDemo {
    public static void main(String[] args) {
        System.out.println(Thread.activeCount());

        System.out.println(Thread.currentThread().getThreadGroup());
    }
}
