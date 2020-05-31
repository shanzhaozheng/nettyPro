package szzi.com.nio;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest {
    public static void main(String[] args) throws IOException {
        /*String str = "hello 单昭铮";
        FileInputStream fileOutputStream = new FileInputStream("G:\\ideaSpace\\nettyPro\\test.txt");
        FileOutputStream fileOutputStream2 = new FileOutputStream("G:\\ideaSpace\\nettyPro\\test2.txt");
        FileChannel channel = fileOutputStream.getChannel();
        FileChannel channel1 = fileOutputStream2.getChannel();
        channel.transferTo(channel.position(),channel.size(),channel1);

        channel.close();
        channel1.close();
        fileOutputStream.close();
        fileOutputStream2.close();*/

        RandomAccessFile randomAccessFile = new RandomAccessFile("G:\\ideaSpace\\nettyPro\\test.txt","rw");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, "bufferedReader.readLine().getBytes()".getBytes().length * 5);
        try {
            for (int i = 0; i < 5; i++) {

                //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                map.put((i+" ").getBytes());
            }
        } finally {
            channel.close();
            randomAccessFile.close();
        }
    }


}
