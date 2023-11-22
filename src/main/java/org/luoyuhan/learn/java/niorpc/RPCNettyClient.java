package org.luoyuhan.learn.java.niorpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.luoyuhan.common.Pair;
import org.luoyuhan.learn.niorpc.service.RpcDoService;
import org.luoyuhan.learn.niorpc.service.RpcSayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author luoyuhan
 */
public class RPCNettyClient {
    private final Logger logger = LoggerFactory.getLogger(RPCNettyClient.class);

    int serializationType;

    private EventLoopGroup workerGroup;
    private Channel channel;

    private final int workerGroupThreads;

    private ClientRpcHandler clientRpcHandler;

    private final Optional<Pair<Long, TimeUnit>> NO_TIMEOUT = Optional.empty();

    public RPCNettyClient(int workerGroupThreads, int serializationType) {
        this.workerGroupThreads = workerGroupThreads;
        this.serializationType = serializationType;
    }

    public static void main(String[] args) {
        int serializationType = 1;
        int port = 12200;
        Map<String, Object> demoServices = new HashMap<>();
        demoServices.put("org.luoyuhan.learn.niorpc.service.RpcSayService", new RpcSayService());
        demoServices.put("org.luoyuhan.learn.niorpc.service.RpcDoService", new RpcDoService());
        RPCNettyServer RPCNettyServer = new RPCNettyServer(3, 1, port, serializationType);
        RPCNettyServer.start(demoServices);
        RPCNettyClient RPCNettyClient = new RPCNettyClient(3, serializationType);
        RPCNettyClient.connect(new InetSocketAddress("127.0.0.1", port));

        try {
            // hello
            RpcRequest request = new RpcRequest();
            request.setTraceId(System.currentTimeMillis() + "");
            request.setClassName("org.luoyuhan.learn.niorpc.service.RpcSayService");
            request.setMethodName("saySomething");
            request.setParameterTypes(new Class[]{String.class});
            request.setParameters(new Object[]{"world"});
            RpcResponse rpcResponse = RPCNettyClient.asyncSend(request, TimeUnit.SECONDS, 10);
            System.out.println(rpcResponse);
            // hello future
            Future<RpcResponse> responseFuture = RPCNettyClient.asyncSendFuture(request, TimeUnit.SECONDS, 10);
            System.out.println(responseFuture);
            // do something
            request.setClassName("org.luoyuhan.learn.niorpc.service.RpcDoService");
            request.setMethodName("doSomething");
            request.setParameters(new Object[]{"learning"});
            rpcResponse = RPCNettyClient.syncSend(request);
            System.out.println(rpcResponse);
            // get future
            System.out.println(responseFuture);
            System.out.println(responseFuture.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RPCNettyClient.close();
            RPCNettyServer.stop();
        }
    }

    public void connect(InetSocketAddress socketAddress) {
        workerGroup = new NioEventLoopGroup(workerGroupThreads);
        clientRpcHandler = new ClientRpcHandler();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new RpcDecoder(RpcResponse.class, serializationType))
                                .addLast(new RpcEncoder(RpcRequest.class, serializationType))
                                .addLast(clientRpcHandler);
                    }
                });
        channel = bootstrap.connect(socketAddress.getAddress().getHostAddress(), socketAddress.getPort())
                .syncUninterruptibly()
                .channel();
    }

    public RpcResponse syncSend(RpcRequest request) throws InterruptedException {
        logger.info("send request: {}", request);
        // 同步发送
        channel.writeAndFlush(request).sync();
        // 获取结果
        return clientRpcHandler.getResponse(request, NO_TIMEOUT);
    }

    public RpcResponse asyncSend(RpcRequest request, TimeUnit timeUnit, long timeout) throws InterruptedException {
        // 异步发送
        channel.writeAndFlush(request);
        // 根据超时时间获取结果
        return clientRpcHandler.getResponse(request, Optional.of(new Pair<>(timeout, timeUnit)));
    }

    public Future<RpcResponse> asyncSendFuture(RpcRequest request, TimeUnit timeUnit, long timeout) throws InterruptedException {
        // 异步发送
        channel.writeAndFlush(request);
        // 根据超时时间获取结果
        return clientRpcHandler.getResponseFuture(request, new Pair<>(timeout, timeUnit));
    }

    public InetSocketAddress getRemoteAddress() {
        SocketAddress remoteAddress = channel.remoteAddress();
        if (!(remoteAddress instanceof InetSocketAddress)) {
            throw new RuntimeException("Get remote address error, should be InetSocketAddress");
        }
        return (InetSocketAddress) remoteAddress;
    }

    public void close() {
        if (null == channel) {
            logger.error("channel is null");
        }
        workerGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
        workerGroup = null;
        channel = null;
    }
}
