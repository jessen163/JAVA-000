package io.kimmking.kmq.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class KmqBroker { // Broker+Connection

    public static final int CAPACITY = 10000;

    private final Map<String, KmqV2> kmqMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name){
        kmqMap.putIfAbsent(name, new KmqV2(name,CAPACITY));
    }

    public KmqV2 findKmq(String topic) {
        return this.kmqMap.get(topic);
    }

    public KmqProducer createProducer() {
        return new KmqProducer(this);
    }

    public KmqConsumer createConsumer() {
        return new KmqConsumer(this);
    }

}
