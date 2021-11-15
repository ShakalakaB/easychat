package fun.aldora.chat;

import java.io.IOException;
import java.net.Socket;

public class Client extends Chat{

    public static void main(String[] args) {
        Client client = new Client();
        Socket clientSocket = client.setClientSocket();
        client.start("client1", "server", clientSocket);
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
