package org.luoyuhan.learn.java.niorpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RPCNettyServer {
    private static final Logger logger = LoggerFactory.getLogger(RPCNettyServer.class);

    private final int ioThreadNum;

    //内核为此套接口排队的最大连接个数，对于给定的监听套接口，内核要维护两个队列，未链接队列和已连接队列大小总和最大值
    private final int backlog;

    private final int port;

    int serializationType;

    private Channel channel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public RPCNettyServer(int ioThreadNum, int backlog, int port, int serializationType) {
        this.ioThreadNum = ioThreadNum;
        this.backlog = backlog;
        this.port = port;
        this.serializationType = serializationType;
    }

    public void start(Map<String, Object> demoServices) {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(this.ioThreadNum);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, backlog)
                //注意是childOption
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline()
                                .addLast(new RpcDecoder(RpcRequest.class, serializationType))
                                .addLast(new RpcEncoder(RpcResponse.class, serializationType))
                                .addLast(new ServerRpcHandler(demoServices));
                    }
                });
        try {
            channel = serverBootstrap.bind("127.0.0.1", port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("NettyRPC server listening on port " + port + " and ready for connections...");

        //do shutdown staff
//        Runtime.getRuntime().addShutdownHook(new Thread(NettyServer.this::stop));
    }

    public void stop() {
        logger.info("server stop");
        if (null == channel) {
            logger.error("channel is null");
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
        bossGroup = null;
        workerGroup = null;
        channel = null;
    }
}
