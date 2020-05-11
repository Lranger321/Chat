package sample.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private static final String REGISTER_FORM = "../views/register.fxml";
    private static final String LOGIN_FORM = "../views/sample.fxml";
    private static final String CHAT_FORM = "../views/chat.fxml";

    private static Boolean loginIsExist = false;
    private static Boolean registerIsExist = false;
    private static Boolean chatIsExist = false;

    private static Scene loginScene;
    private static Scene chatScene;
    private static Scene registerScene;

    public static void artemLox(String scene) {
        FXMLLoader loader = new FXMLLoader();
        String form = "";

        switch (scene) {
            case "register":
                form = REGISTER_FORM;
                break;
            case "login":
                form = LOGIN_FORM;
                break;
        }
        loader.setLocation(MainController.class.getResource(form));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        if (scene.equals("login")) stage.setScene(getLoginScene(root));
        if (scene.equals("register")) stage.setScene(getRegisterScene(root));
        stage.setTitle("Laguna chat");
        stage.show();
    }

    public static void openScene(String login, String password){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource(CHAT_FORM));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(getChatScene(root));
        ChatController controllerEdit = loader.getController();
        stage.setTitle("Laguna chat");
        stage.show();
        controllerEdit.start(login,password);
    }

    private static Scene getLoginScene(Parent root) {
        if (!loginIsExist) {
            loginScene = new Scene(root, 400, 600);
            loginIsExist = true;
        }
        return loginScene;
    }

    private static Scene getRegisterScene(Parent root) {
        if (!registerIsExist) {
            registerScene = new Scene(root, 400, 600);
            registerIsExist = true;
        }
        return registerScene;
    }

    private static Scene getChatScene(Parent root) {
        if (!chatIsExist) {
            chatScene= new Scene(root, 400, 600);
            chatIsExist = true;
        }

        return chatScene;
    }


}
