package szzi.com.netty.api;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author szz
 */
public class ApiClient {

    private static ResultHandler resultHandler = new ResultHandler();

    public void run(FullHttpRequest request) throws URISyntaxException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap().group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpClientCodec())
                            .addLast(new HttpObjectAggregator(1024 * 10 * 1024))
                            .addLast(new HttpContentDecompressor())
                            .addLast("handler", resultHandler);
                        }
                    });
            ChannelFuture future = b.connect("localhost", 8080).sync();
            ChannelFuture sync = future.channel().writeAndFlush(request).sync();
            System.out.println("执行其他业务");

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }


    public void getCollege() throws URISyntaxException {
        URI url = new URI("/consumer/colleges");
        //配置HttpRequest的请求数据和一些配置信息
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, url.toASCIIString());
        request.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8")
                .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
    }


}
