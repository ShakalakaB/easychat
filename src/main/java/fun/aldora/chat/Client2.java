package fun.aldora.chat;

import java.io.IOException;
import java.net.Socket;

public class Client2 extends Chat{

    public static void main(String[] args) {
        Client2 client = new Client2();
        Socket clientSocket = client.setClientSocket();
        client.start("client2", "server", clientSocket);
    }

    @Override
    protected Socket setClientSocket() {
        try {
            return new Socket("127.0.0.1", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
