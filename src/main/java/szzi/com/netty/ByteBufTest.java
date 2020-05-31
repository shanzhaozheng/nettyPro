package szzi.com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,work!", Charset.forName("utf-8"));

        byte[] array = byteBuf.array();

        System.out.println(new String(array,Charset.forName("utf-8")));
        System.out.println(byteBuf);
        System.out.println(byteBuf.arrayOffset());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.capacity());
        System.out.println(byteBuf.getByte(0));
        System.out.println(byteBuf.readableBytes());
    }
}
