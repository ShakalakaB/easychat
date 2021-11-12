package fun.aldora.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public abstract class Chat {
    protected Socket clientSocket;
    protected BufferedReader reader;
    protected PrintWriter writer;
    protected Scanner scanner = new Scanner(System.in);
    protected String myName = "receiver";
    protected int port = 5000;

    abstract void setClientSocket();

    protected void start(String talkToName) {
        try {
            writer = new PrintWriter(clientSocket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread sender = new Thread(getSenderRunnable());
            sender.start();

            Thread receiver = new Thread(getReaderRunnable(talkToName));
            receiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                    System.out.println("connection closed");

                    writer.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Runnable getSenderRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                String msg;
                while (true) {
                    msg = scanner.nextLine();
                    writer.println(msg);
                    writer.flush();
                }
            }
        };
    }
}
