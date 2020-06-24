package szzi.com.netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MessageDecode extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int i = byteBuf.readInt();
        byte[] bytes = new byte[i];
        byteBuf.readBytes(bytes);
        Message message = new Message();
        message.setLen(i);
        message.setMessage(new String(bytes,"UTF-8"));
        list.add(message);
    }
}
