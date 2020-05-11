import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {

    DataBase dataBase;


    @Test
    void registerClient() {
        new DataBase();
        HashMap<String,String> request = new HashMap<>();
        HashMap<String,String> response = new HashMap<>();
        response.put("status","ok");
        request.put("login","4");
        request.put("name","3");
        request.put("lastName","4");
        request.put("gender","Male");
        request.put("password","3");
        assertEquals(response,DataBase.registerClient(request));
        response.clear();
        response.put("status","error");
        response.put("error_type","login_used");
        assertEquals(response,DataBase.registerClient(request));
    }

    @Test
    void login() {
    }

    @Test
    void getChat() {
    }

    @Test
    void addMessage() {
        new DataBase();
        HashMap<String,String> request = new HashMap<>();
        request.put("login","lranger123");
        request.put("time", String.valueOf((int) new Date().getTime()/1000L));
        request.put("message","LEXA KRASAVA");
        DataBase.addMessage(request);
    }
}