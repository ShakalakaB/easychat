package fun.aldora.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public abstract class Chat {
    protected int port = 5000;

    abstract Socket setClientSocket();

    protected void start(String fromName, String toName, Socket clientSocket) {
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread sender = new Thread(getSenderRunnable(writer), fromName + " sender");
            sender.start();

            Thread receiver = new Thread(getReaderRunnable(toName, clientSocket, reader, writer), fromName + " receiver");
            receiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                System.out.println("connection closed");

                writer.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private Runnable getSenderRunnable(final PrintWriter writer) {
        return () -> {
            Scanner scanner = new Scanner(System.in);
            String msg;
            while (true) {
                msg = scanner.nextLine();
                writer.println(msg);
                writer.flush();
            }
        };
    }
}
