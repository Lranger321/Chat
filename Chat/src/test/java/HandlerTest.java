// This is an independent project of an individual developer. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class HandlerTest {

    @Test
    void register() {
        new DataBase();
        Assert.assertEquals("{\"method\":\"register\",\"status\":\"ok\"}",
                Handler.response("{\"method\":\"register\",\"login\":\"123\",\"password\":\"123\"," +
                "\"name\":\"name\",\"last_name\":\"last\",\"gender\":\"Male\"}",null));
        Assert.assertEquals("{\"method\":\"register\",\"error_type\":\"login_used\",\"status\":\"error\"}",
                Handler.response("{\"method\":\"register\",\"login\":\"123\",\"password\":\"123\"," +
                        "\"name\":\"nae\",\"last_name\":\"last\",\"gender\":\"Male\"}",null));
    }

    @Test
    void login() {
        new DataBase();
        Handler.response("{\"method\":\"register\",\"login\":\"123\",\"password\":\"123\"," +
                "\"name\":\"nae\",\"last_name\":\"last\",\"gender\":\"Male\"}",null);
        Assert.assertEquals("{\"method\":\"login\",\"status\":\"ok\"}",
                Handler.response("{\"method\": \"login\",\"login\":\"123\"" +
                ",\"password\":\"123\"}",null));
        Assert.assertEquals("{\"method\":\"login\",\"error_type\":\"wrong_password\",\"status\":\"error\"}",
                Handler.response("{\"method\": \"login\",\"login\":\"123\",\"password\":\"321\"}", null));
    }

    @Test
    void getChat() {
        new DataBase();
        Handler.response("{\"method\":\"send_message\",\"login\":\"123\"," +
                "\"time\":\"230120\"," +"\"message\":\"msg\"}",null);
        Handler.response("{\"method\":\"send_message\",\"login\":\"123\"," +
                "\"time\":\"230120\"," +"\"message\":\"msg\"}",null);
        assertEquals("{\"method\":\"get_chat\",\"chat\":[{\"time\":\"230120\",\"login\":\"123\",\"message\":\"msg\"}," +
                        "{\"time\":\"230120\",\"login\":\"123\",\"message\":\"msg\"}]}",
                Handler.response("{\"method\":\"get_chat\"}", null));
    }

}