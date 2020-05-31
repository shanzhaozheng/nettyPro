package szzi.com.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class MyHttpHandler extends SimpleChannelInboundHandler<HttpObject> {


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest){
            System.out.println("msg类型"+httpObject.getClass());
            System.out.println("客户端地址"+channelHandlerContext.channel().remoteAddress());

            HttpRequest request = (HttpRequest)httpObject;
            System.out.println(request.uri());
            if ("/fvaicon.ico".equals(request.uri())){
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK,
                        Unpooled.copiedBuffer(" 拦截fvaicon",CharsetUtil.UTF_8));
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
                // response.headers().add(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());

                channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                return;
            }
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.copiedBuffer("Hello 我是netty服务器",CharsetUtil.UTF_8));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
           // response.headers().add(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());

            channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
