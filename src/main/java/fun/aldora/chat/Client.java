package fun.aldora.chat;

import java.io.IOException;
import java.net.Socket;

public class Client extends Chat{

    public static void main(String[] args) {
        Client client = new Client();
        client.setClientSocket();
        client.start("server");
    }

    @Override
    protected void setClientSocket() {
        try {
            this.clientSocket = new Socket("127.0.0.1", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
