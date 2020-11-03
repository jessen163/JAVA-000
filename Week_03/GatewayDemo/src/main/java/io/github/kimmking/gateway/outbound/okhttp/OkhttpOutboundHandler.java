package io.github.kimmking.gateway.outbound.okhttp;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class OkhttpOutboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(OkhttpOutboundHandler.class);
    private String proxyServer;

    public OkhttpOutboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        //logger.info("channelRead流量接口请求开始，时间为{}", startTime);
        String uri = "";
        OkHttpClient client = new OkHttpClient();
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            uri = fullRequest.uri();
            System.out.println("uri:" + uri);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
        Request request = new Request.Builder().url(this.proxyServer+uri).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
