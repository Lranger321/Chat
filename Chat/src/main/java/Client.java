// This is an independent project of an individual developer. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
import java.io.*;
import java.net.Socket;

public class Client {

   private BufferedWriter out;
   private BufferedReader in;
   private Socket socket;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            while (true) {
                try {
                    String msg = in.readLine();
                    sendMsg(Handler.response(msg,this));
                } catch (IOException e) {
                    e.printStackTrace();
                    ClientsList.removeClient(this);
                    break;
                }
            }
        }).start();


    }

    public synchronized void sendMsg(String msg) {
        try {
            System.out.println("To client: "+msg);
            out.write(msg + "\r\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
