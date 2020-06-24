package szzi.com.netty.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bootstrapGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bootstrapGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerHandler());

            ChannelFuture channelFuture = serverBootstrap.bind(6669).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) System.out.println("哈哈");
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            bootstrapGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
