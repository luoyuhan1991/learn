package org.luoyuhan.learn.java.niorpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.util.Map;

/**
 * @author luoyuhan
 */
@Slf4j
public class ServerRpcHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private final Map<String, Object> serviceMapping;

    public ServerRpcHandler(Map<String, Object> serviceMapping) {
        this.serviceMapping = serviceMapping;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) {
        RpcResponse response = new RpcResponse();
        response.setTraceId(rpcRequest.getTraceId());
        try {
            log.info("server handle request: {}", rpcRequest);
            Object result = handle(rpcRequest);
            response.setResult(result);
        } catch (Throwable t) {
            response.setError(t);
        }
        channelHandlerContext.writeAndFlush(response);
    }

    private Object handle(RpcRequest request) throws Throwable {
        String className = request.getClassName();
        Object serviceBean = serviceMapping.get(className);

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("cause.getMessage: {}", cause.getMessage());
        RpcResponse response = new RpcResponse();
        if (cause instanceof RpcException) {
            response.setTraceId(((RpcException) cause).getTraceId());
        }
        response.setError(cause);
        ctx.writeAndFlush(response);
    }
}
