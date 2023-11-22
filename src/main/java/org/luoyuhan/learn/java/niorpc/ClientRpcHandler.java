package org.luoyuhan.learn.java.niorpc;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.luoyuhan.learn.common.Pair;

import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author luoyuhan
 */
@Slf4j
@ChannelHandler.Sharable
public class ClientRpcHandler extends SimpleChannelInboundHandler<RpcResponse> {
    //用blocking queue主要是用阻塞的功能，省的自己加锁
    private final ConcurrentHashMap<String, BlockingQueue<RpcResponse>> responseMap = new ConcurrentHashMap<>();

    //messageReceived
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) {
        log.info("receive response: {}", rpcResponse);
        // 获取到结果，根据trace ID放入对应的阻塞队列，此过程是自动的，调用时机由服务端决定。客户端需要结果的话从队列中获取。
        BlockingQueue<RpcResponse> queue = responseMap.get(rpcResponse.getTraceId());
        queue.add(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

    // 根据trace ID获取结果
    public RpcResponse getResponse(RpcRequest request, Optional<Pair<Long, TimeUnit>> timeout) throws InterruptedException {
        responseMap.putIfAbsent(request.getTraceId(), new LinkedBlockingQueue<>(1));
        RpcResponse response;
        try {
            BlockingQueue<RpcResponse> queue = responseMap.get(request.getTraceId());
            if (timeout == null || !timeout.isPresent()) {
                response = queue.take();
            } else {
                response = queue.poll(timeout.get().getKey(), timeout.get().getValue());
            }
        } finally {
            responseMap.remove(request.getTraceId());
        }
        return response;
    }

    // 根据trace ID获取结果
    public Future<RpcResponse> getResponseFuture(RpcRequest request, Pair<Long, TimeUnit> timeout) throws InterruptedException {
        responseMap.putIfAbsent(request.getTraceId(), new LinkedBlockingQueue<>(1));
        return CompletableFuture.supplyAsync(() -> {
            RpcResponse response = null;
            try {
                BlockingQueue<RpcResponse> queue = responseMap.get(request.getTraceId());
                if (timeout == null) {
                    response = queue.take();
                } else {
                    response = queue.poll(timeout.getKey(), timeout.getValue());
                }
            } catch (Exception e) {
                log.error("getResponseFuture Error", e);
            } finally {
                responseMap.remove(request.getTraceId());
            }
            return response;
        });
    }
}
