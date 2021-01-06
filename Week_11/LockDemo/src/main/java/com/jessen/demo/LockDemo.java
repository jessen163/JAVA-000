package com.jessen.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class LockDemo {
    public static String LOCK = "lock_storage";

    public static int amount = 100;

    private final static int EXPIRE = 30;

    /**
     * 加锁
     *
     * @param jedisPool
     * @param lockKey
     * @param lockValue
     * @param seconds
     * @return
     */
    public static boolean lock(JedisPool jedisPool, String lockKey, String lockValue, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            return "OK".equals(jedis.set(lockKey, lockValue, "NX", "EX", seconds));
        }
    }

    /**
     * 释放锁
     *
     * @param lock lock value
     * @return release lock
     */
    public static boolean release(JedisPool jedisPool, String lock) {
        String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then " + "return redis.call('del',KEYS[1]) else return 0 end";
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.eval(luaScript, Collections.singletonList(lock), Collections.singletonList(lock)).equals(1L);
        }
    }

    public static JedisPool getJedisPool() {
        //创建连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(25);
        config.setMaxIdle(20);
        config.setMinIdle(5);
        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);

        return pool;
    }

    /**
     * 减去库存
     *
     * @param num
     * @return
     */
    public static boolean minusStorage(int num) {
        JedisPool pool = getJedisPool();
        while (!lock(pool, LOCK, LOCK, 60)) {
            // 获取锁失败
            System.out.println("get lock fail");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("get lock success");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        amount -= num;
        release(pool, LOCK);
        System.out.println("lock end");
        return true;
    }


    public static void main(String[] args) throws InterruptedException {
        //创建连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(25);
        config.setMaxIdle(20);
        config.setMinIdle(5);
        try (
                JedisPool pool = new JedisPool(config, "127.0.0.1", 6379)) {

            boolean isLock = lock(pool, "test", "123456", 60);

            System.out.println("first Lock-----isLock:" + isLock);
            isLock = lock(pool, "test", "123456", 60);
            System.out.println("second Lock-----isLock:" + isLock);

            release(pool, "test");
            System.out.println("release lock-----isLock:" + isLock);
        }

        ExecutorService executorService = new ThreadPoolExecutor(2, 2,
                0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(512), // 使用有界队列，避免OOM
                new ThreadPoolExecutor.DiscardPolicy());
        int totalminus = 0;
        for (int i = 0; i < 10; i++) {
            int num = new Random().nextInt(10);
            totalminus += num;
            executorService.submit(() -> {
                System.out.println("减去库存:" + num);
                minusStorage(num);
            });
            System.out.println("库存剩余数量:" + amount);
        }
        Thread.sleep(15000);
        System.out.println("库存减去数量:" + totalminus);
        System.out.println("库存剩余数量:" + amount);
        executorService.shutdown();
        System.out.println("库存剩余数量:" + amount);


    }
}
