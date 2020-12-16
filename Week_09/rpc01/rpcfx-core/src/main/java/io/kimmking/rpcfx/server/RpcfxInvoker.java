package io.kimmking.rpcfx.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.exception.RpcfxException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RpcfxInvoker implements ApplicationContextAware {

    private RpcfxResolver resolver;

    private ApplicationContext applicationContext;

    public RpcfxInvoker(RpcfxResolver resolver){
        this.resolver = resolver;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();

        // 作业1：改成泛型和反射
        Class<?> cla = null;
        try {
            cla = Class.forName(serviceClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.setException(new RpcfxException("not found class", e));
            response.setStatus(false);
            return response;
        }
        Object service = resolver.resolve(serviceClass);//this.applicationContext.getBean(serviceClass);

        try {
            Method method = resolveMethodFromClass(cla, request.getMethod());
            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
            // 两次json序列化能否合并成一个
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch ( IllegalAccessException | InvocationTargetException e) {

            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(new RpcfxException("IllegalAccessException|InvocationTargetException error", e));
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }

}
