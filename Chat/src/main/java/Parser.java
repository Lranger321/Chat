// This is an independent project of an individual developer. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.*;

public class Parser {

    public static String toJson(Map<String, String> response) {
        JSONObject json = new JSONObject();
        Set<String> keys = response.keySet();

        for (String key : keys) {
            try {
                json.put(key, response.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json.toString();
    }

    public static String toJson(ArrayList<HashMap<String,String>> list) {
        JSONObject json = new JSONObject();
        try {
            json.put("method","get_chat");
            json.put("chat",list);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }


    //@TODO
    public static HashMap<String, String> parse(String msg) {
        HashMap<String, String> response = new HashMap<>();
        try {
            JSONObject json = new JSONObject(msg);
            String method = json.getString("method");
            response.put("method", method);
            switch (method) {
                case "register":
                    response.put("login", json.getString("login"));
                    response.put("password", json.getString("password"));
                    response.put("gender", json.getString("gender"));
                    response.put("name", json.getString("name"));
                    response.put("last_name", json.getString("last_name"));
                    break;
                case "login":
                    response.put("login", json.getString("login"));
                    response.put("password", json.getString("password"));
                    break;
                case "send_message":
                    response.put("login", json.getString("login"));
                    response.put("message", json.getString("message"));
                    response.put("time", json.getString("time"));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }


}
