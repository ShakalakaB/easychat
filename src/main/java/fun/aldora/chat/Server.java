package fun.aldora.chat;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Chat {
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Server server = new Server();
        server.setServerSocket();
        server.setClientSocket();
        server.start("client");
    }

    protected void setServerSocket() {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void setClientSocket() {
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Runnable getReaderRunnable(final String talkToName) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = reader.readLine();

                    while (msg != null) {
                        System.out.println(talkToName + ": " + msg);
                        msg = reader.readLine();
                    }
                    System.out.println("client close connection");

                    writer.close();
                    clientSocket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
