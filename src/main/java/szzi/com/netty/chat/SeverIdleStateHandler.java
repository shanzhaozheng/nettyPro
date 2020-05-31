package szzi.com.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class SeverIdleStateHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent state = (IdleStateEvent) evt;
            switch (state.state()){
                case READER_IDLE:
                    System.out.println(ctx.channel().remoteAddress()+"超时:"+"读心跳检测~");
                    break;
                case WRITER_IDLE:
                    System.out.println(ctx.channel().remoteAddress()+"超时:"+"写心跳检测~");
                    break;
                case ALL_IDLE:
                    System.out.println(ctx.channel().remoteAddress()+"超时:"+"读写心跳检测~");
                    break;
            }
        }
    }
}
