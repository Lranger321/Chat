import java.util.HashMap;
import java.util.Map;

public class Handler {

    //@TODO
    public static String response(String msg,Client client){
        Map<String,String> response = new HashMap<>();
        HashMap<String,String> request = Parser.parse(msg);
        String method = request.get("method");
        switch (method){
            case "register":
                response = DataBase.registerClient(request);
                break;
            case "login":
                response = DataBase.login(request);
                break;
            case "send_message":
                response = request;
                for (Client con : ClientsList.getClients()) {
                    if(!con.equals(client))
                        con.sendMsg(Parser.toJson(response));
                }
                DataBase.addMessage(request);
                break;
            case "get_chat":
                return Parser.toJson(DataBase.getChat());

        }
        response.put("method",method);
        return Parser.toJson(response);
    }


}
