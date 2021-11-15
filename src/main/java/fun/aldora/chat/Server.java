package fun.aldora.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server extends Chat {
    private ServerSocket serverSocket;
    private List<PrintWriter> writerList = new ArrayList<>();

    public static void main(String[] args) {
        Server server = new Server();
        server.setServerSocket();
        server.sendSystemInput();

        int i = 1;
        while (!server.serverSocket.isClosed()) {
            Socket clientSocket = server.setClientSocket();
            server.start("server", "client" + i++, clientSocket);
        }
    }

    @Override
    protected void start(String fromName, String toName, Socket clientSocket) {
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writerList.add(writer);

            Thread receiver = new Thread(getReaderRunnable(toName, clientSocket, reader, writer), fromName + " receiver");
            receiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    Socket setClientSocket() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Runnable getReaderRunnable(final String talkToName,
                                         final Socket clientSocket,
                                         final BufferedReader reader,
                                         final PrintWriter writer) {
        return () -> {
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
        };
    }

    private void sendSystemInput() {
        Thread thread = new Thread(getSystemInRunnable(), "read-sys-input");
        thread.start();
    }

    private Runnable getSystemInRunnable() {
        return () -> {
            Scanner scanner = new Scanner(System.in);
            String msg;
            while (true) {
                msg = scanner.nextLine();
                final String finalMsg = msg;
                writerList.forEach(writer -> {
                    writer.println(finalMsg);
                    writer.flush();
                });
            }
        };
    }

    private void setServerSocket() {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
