package szzi.com.bio;

import java.io.*;
import java.net.Socket;

public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket localhost = new Socket("localhost", 6666);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(localhost.getOutputStream());
        outputStreamWriter.write("sb");
        outputStreamWriter.flush();
        localhost.close();
    }
}
