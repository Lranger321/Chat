// This is an independent project of an individual developer. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
import java.util.ArrayList;

public class ClientsList {

    static ArrayList<Client> clients = new ArrayList<>();

    public static void addClient(Client client) {
        clients.add(client);
        System.out.println("New client, there " + clients.size());
    }

    public static ArrayList<Client> getClients() {
        return clients;
    }

    public synchronized static void removeClient(Client client) {
        clients.remove(client);
    }

}
