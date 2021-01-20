package io.kimmking.kmq.core;

import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class KmqV2 {

    public KmqV2(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new KmqQueue(capacity);
    }

    private String topic;

    private int capacity;

    private KmqQueue<KmqMessage> queue;

    public boolean send(KmqMessage message) {
        return queue.offer(message);
    }

    public KmqMessage poll() {
        return queue.poll();
    }

    @SneakyThrows
    public KmqMessage poll(long timeout) {
        return queue.poll(timeout, TimeUnit.MILLISECONDS);
    }

    public void ack() {
        queue.ack();
    }
}
