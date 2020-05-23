package sample.Controllers;

import Server.Parser;
import Server.Server;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

public class RegisterController {
    @FXML
    private Button signUpButton;

    @FXML
    private TextField loginFiled;

    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private RadioButton otherRadio;

    @FXML
    private Button backButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField locationField;


    @FXML
    void initialize() {
        maleRadio.setOnAction(actionEvent -> {
            femaleRadio.setSelected(false);
            otherRadio.setSelected(false);
        });

        femaleRadio.setOnAction(actionEvent -> {
            maleRadio.setSelected(false);
            otherRadio.setSelected(false);
        });

        otherRadio.setOnAction(actionEvent -> {
            maleRadio.setSelected(false);
            femaleRadio.setSelected(false);
        });

        backButton.setOnAction(actionEvent -> {
            backButton.getScene().getWindow().hide();
            MainController.changeScene("login");
        });

        signUpButton.setOnAction(actionEvent -> {
            HashMap<String, String> request = new HashMap<>();
            request.put("method", "register");
            request.put("name", nameField.getText());
            request.put("last_name", lastNameField.getText());
            request.put("login", loginFiled.getText());
            request.put("password", passwordField.getText());
            request.put("gender", (maleRadio.isSelected()) ? "male" : (femaleRadio.isSelected()) ? "female" : "other");
            request.put("location", locationField.getText());
            sendToServer(Parser.Cript(request));
        });
    }

    private void sendToServer(String msg) {
        Server.sendMsg(msg);
        BufferedReader in = Server.getIn();
        while (true) {
            try {
                String response = in.readLine();
                HashMap<String, String> map = Parser.parse(response);
                if (map.get("method").equals("register")) {

                    String status = map.get("status");
                    if (status.equals("ok")) {
                        backButton.getScene().getWindow().hide();
                        MainController.changeScene("login");
                        break;
                    } else {
                        String error = map.get("error_type");
                        if (error.equals("login_used")) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);

                            alert.setTitle("Information");
                            alert.setHeaderText(null);
                            alert.setContentText("Login is already used");

                            alert.showAndWait();
                            break;
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);

                            alert.setTitle("Information");
                            alert.setHeaderText(null);
                            alert.setContentText("Error on server ");

                            alert.showAndWait();
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
