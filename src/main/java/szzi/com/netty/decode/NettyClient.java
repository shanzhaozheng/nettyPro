package szzi.com.netty.decode;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bootstrapGroup = new NioEventLoopGroup();

        try {
            Bootstrap serverBootstrap = new Bootstrap()
                    .group(bootstrapGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new MessageDecode())
                                    .addLast(new MessageEncode())
                                    .addLast(new ClientHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.connect("localhost",6668).sync();

            channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
                if (channelFuture1.isSuccess()){
                    System.out.println("连接服务成功！");
                }
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            bootstrapGroup.shutdownGracefully();
        }

    }
}
