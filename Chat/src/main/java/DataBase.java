// This is an independent project of an individual developer. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {

    private static Connection dbConnection;

    public DataBase() {
        String connectionString = "jdbc:mysql://127.0.0.1:3306/chat?&useUnicode=true&serverTimezone=UTC&useSSL=false";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            dbConnection = DriverManager.getConnection(connectionString, "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(dbConnection);
    }

    public static HashMap<String, String> registerClient(HashMap<String, String> request) {
        HashMap<String, String> response = new HashMap<>();
        if (inBase(request.get("login"))) {
            response.put("status", "error");
            response.put("error_type", "login_used");
        } else {
            try {
                String name = request.get("name");
                String lastName = request.get("lastName");
                String login = request.get("login");
                String password = request.get("password");
                String gender = request.get("gender");
                Statement statement = dbConnection.createStatement();
                statement.execute("INSERT into clients (idClients,name,lastname,login,password,gender)" +
                        " VALUES (" + countOfClients() + ",'" + name + "','" + lastName + "','" + login +
                        "','" + password + "','" + gender + "')");
                response.put("status", "ok");
            } catch (SQLException e) {
                response.put("status", "error");
                response.put("error_type", "sql_error");
                e.printStackTrace();
            }

        }
        return response;
    }

    public static HashMap<String, String> login(HashMap<String, String> request) {
        HashMap<String, String> response = new HashMap<>();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT password from clients " +
                    "WHERE login='" + request.get("login") + "'");
            set.next();
            if (set.getString("password").equals(request.get("password"))) {
                response.put("status", "ok");
            } else {
                response.put("status", "error");
                response.put("error_type", "wrong_password");
            }
        } catch (SQLException e) {
            response.put("status", "error");
            response.put("error_type", "sql_error");
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<HashMap<String,String>> getChat() {
        ArrayList<HashMap<String,String>> list = new ArrayList<>();

        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM messages");
            while (set.next()) {
               HashMap<String,String> map = new HashMap<>();
               map.put("login",set.getNString("login"));
               map.put("message",set.getNString("message"));
               map.put("time",set.getNString("time"));
               list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Boolean addMessage(HashMap<String, String> request) {
        try {
            Statement statement = dbConnection.createStatement();
            statement.execute("INSERT INTO messages (login,message,time)" +
                    "VALUES ('" + request.get("login") + "','" + request.get("message") +
                    "','" + request.get("time") + "')");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Boolean inBase(String login) {
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT login from clients");
            while (set.next()) {
                if (login.equals(set.getNString("login"))) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static int countOfClients() {
        int count = 1;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet set = statement.executeQuery("SELECT login from clients");
            while (set.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}
