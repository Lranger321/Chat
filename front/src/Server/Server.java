package Server;

import java.io.*;
import java.net.Socket;

public class Server {

    private static BufferedReader in;
    private static BufferedWriter out;

    public static void makeConnection(){
        try {
            Socket socket = new Socket("localhost",8500);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedReader getIn(){
        return in;
    }

    public static void sendMsg(String msg){
        try {
            System.out.println(msg);
            out.write(msg+"\r\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
