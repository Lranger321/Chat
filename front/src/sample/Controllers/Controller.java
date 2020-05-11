package sample.Controllers;

import Server.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField loginFiled;

    @FXML
    private PasswordField passwordField;

    @FXML
    void initialize() {
        signInButton.setOnAction(actionEvent -> {
            HashMap<String, String> request = new HashMap<>();
            request.put("login", loginFiled.getText());
            request.put("password", passwordField.getText());
            request.put("method", "login");
            System.out.println(request);
            Server.sendMsg(Parser.Cript(request));
            new Thread(() -> {
                BufferedReader in = Server.getIn();
                try {
                    while (true) {
                        String msg = in.readLine();
                        HashMap<String, String> map = Parser.parse(msg);
                        if (map.get("method").equals("login")) {
                            if (map.get("status").equals("ok")) {
                                Platform.runLater(()->{
                                    signInButton.getScene().getWindow().hide();
                                    Thread.currentThread().interrupt();
                                    MainController.openScene(loginFiled.getText(),passwordField.getText());
                                });
                                break;
                            } else {
                                System.out.println("error was found");
                            }

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();
        });

        signUpButton.setOnAction(actionEvent -> {
            loginFiled.getScene().getWindow().hide();
            MainController.artemLox("register");
        });
    }


}
