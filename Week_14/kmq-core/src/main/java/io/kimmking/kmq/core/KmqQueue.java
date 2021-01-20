package io.kimmking.kmq.core;

import java.util.concurrent.TimeUnit;

public class KmqQueue<T> {
    T[] arr;
    // Define capacity constant: CAPACITY
    private static final int CAPACITY = 1024;
    // Define capacity of queue
    private int capacity;
    // Front of queue
    private int front;
    // Tail of queue
    private int tail;
    // Front of queue uncheck
    private int unCheckFront;
    // Tail of queue uncheck
    private int unCheckTail;

    public KmqQueue() {
        this.capacity = CAPACITY;
        this.arr = (T[])new Object[capacity];
    }

    public KmqQueue(int capacity) {
        this.capacity = capacity;
        this.arr = (T[])new Object[capacity];
    }

    public boolean ack() {
        front = unCheckFront;
        tail = unCheckTail;
        return true;
    }

    public boolean offer(T t) {
        if (unCheckFront >= capacity) {
            return false;
        }
        arr[unCheckFront] = t;
        unCheckFront = unCheckFront+1;
        return true;
    }

    public T poll() {
        if (unCheckFront >= capacity) {
            return null;
        }
        T t = arr[unCheckTail];
        if (t == null) {
            return null;
        }

        unCheckTail = unCheckTail + 1;
        return t;
    }

    public T poll(long timeout, TimeUnit unit) {
        return null;
    }
}
