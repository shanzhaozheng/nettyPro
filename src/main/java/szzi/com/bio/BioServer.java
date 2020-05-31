package szzi.com.bio;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class BioServer {

    private static ThreadPoolExecutor pool;

    private static boolean flag = true;

    static {
        pool = new ThreadPoolExecutor(1,10,0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("启动服务");
        while (flag){
            final Socket accept = serverSocket.accept();
            pool.execute(new Runnable() {
                @lombok.SneakyThrows
                public void run() {
                    char [] buf = new char[1024 * 2];
                    InputStreamReader inputStreamReader = new InputStreamReader(accept.getInputStream(),"UTF-8");
                    int read;
                    while ((read = inputStreamReader.read(buf)) != -1){
                        String s = new String(buf);
                        System.out.println(s);
                    }
                }
            });
        }
    }
}
