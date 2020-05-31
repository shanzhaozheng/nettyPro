package szzi.com.netty.chat;

import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.TimeUnit;

public class ChatServer {
    private int port = 6669;

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap().group(bossGroup, workGroup)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new IdleStateHandler(2,5,7, TimeUnit.SECONDS))
                                .addLast(new SeverIdleStateHandler())
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new ChatServerHandler());
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        channelFuture.addListener(future -> {
            if (future.isSuccess())
                System.out.println("服务启动成功！");

        });
        channelFuture.channel().closeFuture().sync();
        try {
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();

        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ChatServer(6669).run();
    }
}
