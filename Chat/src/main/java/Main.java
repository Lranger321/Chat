// This is an independent project of an individual developer. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    static ServerSocket serverSocket;

    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(8500);
            new Thread(() -> {
                while (true) {
                    try {
                        ClientsList.addClient(new Client(serverSocket.accept()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new DataBase();
    }

}
