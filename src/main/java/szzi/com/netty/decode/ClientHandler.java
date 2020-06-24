package szzi.com.netty.decode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            String info = "hello, wordgagagaggagagagagagaga";
            message.setLen(info.getBytes().length);
            message.setMessage(info);
            ctx.writeAndFlush(message);
        }
    }




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
