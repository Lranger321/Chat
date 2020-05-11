package Server;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Parser {

    public static String Cript(HashMap<String, String> request) {
        JSONObject json = new JSONObject();
        Set<String> keys = request.keySet();
        Iterator var3 = keys.iterator();

        while (var3.hasNext()) {
            String key = (String) var3.next();
            try {
                json.put(key, request.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return String.valueOf(json);
    }


    public static HashMap<String, String> parse(String msg) {
        HashMap<String, String> response = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(msg);
            String method = jsonObject.getString("method");
            response.put("method", method);
            switch (method) {
                case "login":
                case "register":
                    String status = jsonObject.getString("status");
                    response.put("status", status);
                    if(status.equals("error")){
                        response.put("error_type",jsonObject.getString("status"));
                    }
                    break;
                case "send_message":
                    response.put("login", jsonObject.getString("login"));
                    response.put("message", jsonObject.getString("message"));
                    response.put("time", toDate(jsonObject.getString("time")));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static ArrayList<HashMap<String, String>> parseChat(String msgFromServer) {
        JSONObject json;
        JSONArray array;
        ArrayList<HashMap<String,String>> response = new ArrayList<>();
        try {
            json = new JSONObject(msgFromServer);
            array = json.getJSONArray("chat");
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = new JSONObject(array.getString(i));
                HashMap<String,String> map = new HashMap<>();
                map.put("time", toDate(jsonObject.getString("time")));
                map.put("message",jsonObject.getString("message"));
                map.put("login",jsonObject.getString("login"));
                response.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static String toDate(String text){
        StringBuilder date = new StringBuilder();
        for(int i=0;i<6;i++){
            if(i == 2 || i == 4){
                date.append(".");
            }
            date.append(text.toCharArray()[i]);
        }
        return String.valueOf(date);
    }


}
