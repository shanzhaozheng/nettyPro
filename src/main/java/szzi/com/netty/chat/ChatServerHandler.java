package szzi.com.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.nio.channels.SocketChannel;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup  channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+":"+"上线了");
        channels.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+":"+"下线了");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.writeAndFlush(ctx.channel().remoteAddress()+":"+"上线了");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.writeAndFlush(ctx.channel().remoteAddress()+":"+"下线了");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        for (Channel channel : channels) {
            if (channel != channelHandlerContext.channel()){
                channel.writeAndFlush(channelHandlerContext.channel().remoteAddress()+":"+s);
            }
        }
    }

}
