package szzi.com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {
    private static final int PORT = 6668;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bootstrapGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bootstrapGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();

            channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
                if (channelFuture1.isSuccess()){
                    System.out.println("监听端口"+PORT+"成功！");
                }else {
                    System.out.println("监听端口失败");
                }
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            bootstrapGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

}
