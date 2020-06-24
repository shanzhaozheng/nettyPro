package szzi.com.netty.decode;

import lombok.Data;

@Data
public class Message {

    private int len;

    private String message;
}
