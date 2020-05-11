package sample.Controllers;

import Server.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ChatController {

    @FXML
    private Button sendButton;

    @FXML
    private TextArea chat;

    @FXML
    private TextField msgField;


    private static String login;
    private static String password;

    @FXML
    void initialize() {
        sendButton.setOnAction(actionEvent -> {
            Calendar calendar = Calendar.getInstance();
            HashMap<String, String> request = new HashMap<>();
            request.put("status", "ok");
            request.put("method", "send_message");
            request.put("login", login);
            request.put("message", msgField.getText());
            SimpleDateFormat format = new SimpleDateFormat("YYMMdd");
            request.put("time", format.format(calendar.getTime()));
            Server.sendMsg(Parser.Cript(request));
        });
    }

    public synchronized void start(String login, String password) {

        this.login = login;
        this.password = password;
        BufferedReader in = Server.getIn();

        new Thread((() -> {
            while (true) {
                try {
                    String msgFromServer = in.readLine();
                    System.out.println("From server: " + msgFromServer);
                    HashMap<String, String> responce = Parser.parse(msgFromServer);
                    if (responce.get("method").equals("get_chat")) {
                        ArrayList<HashMap<String, String>> chat = Parser.parseChat(msgFromServer);
                        for (int i = 0; i < chat.size(); i++) {
                            HashMap<String, String> message = chat.get(i);
                            addToChat(message.get("time"), message.get("login"),
                                    message.get("message"));
                        }
                    }
                    if(responce.get("method").equals("send_message")){
                        addToChat(responce.get("time"), responce.get("login"),
                                responce.get("message"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        )).start();

        HashMap<String, String> request = new HashMap<>();
        request.put("method", "get_chat");
        request.put("login", login);
        Server.sendMsg(Parser.Cript(request));


    }

    private void addToChat(String time, String login, String message) {
        chat.appendText(time + " " + login + ": " + message + "\n\r");
    }
}
