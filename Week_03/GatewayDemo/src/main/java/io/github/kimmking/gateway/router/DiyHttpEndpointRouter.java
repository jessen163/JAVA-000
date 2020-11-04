package io.github.kimmking.gateway.router;

import io.netty.util.internal.StringUtil;

import java.util.List;
import java.util.Random;

public class DiyHttpEndpointRouter implements HttpEndpointRouter {
    private String proxyServer;

    public DiyHttpEndpointRouter(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public String route(List<String> endpoints) {
        if (endpoints == null || endpoints.size() == 0) {
            throw new RuntimeException("参数错误");
        }
        Random random = new Random();
        int index = random.nextInt(endpoints.size());
        return endpoints.get(index);
    }
}
