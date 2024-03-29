package com.blackbank.flyingdollar;

import static com.blackbank.flyingdollar.ClientStatus.DEACTIVE;
import static com.blackbank.flyingdollar.ClientStatus.DELETE;
import static com.blackbank.flyingdollar.ClientStatus.NORMAL;
import static com.blackbank.flyingdollar.ClientType.ACCOUNT;
import static com.blackbank.flyingdollar.ClientType.ADMIN;
import static com.blackbank.flyingdollar.ClientType.OPERATOR;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ClientManager {
    private HashMap<String, Client> clients = new HashMap<>();
    private ArrayList<String> usernames = new ArrayList<>();
    private File data;

    public ClientManager(String dataPath)
    {
        data = new File(dataPath);

        try {
            if (!data.isFile())
                data.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData()
    {
        String dataString = null;

        try {
            dataString = new String(Files.readAllBytes(data.toPath()), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataReader reader = new DataReader(dataString);
        ArrayList<HashMap<String, String>> maps = reader.parseMaps();

        for (HashMap<String, String> map : maps) {
            Client client = new Client();
            BankUtils.parseMap(client, map);
            if (client.isDeleted())
                continue;
            clients.put(client.username, client);
            usernames.add(client.username);
        }
    }

    public void deloadData()
    {
        try (PrintWriter out = new PrintWriter(data)) {
            for (String username : usernames) {
                Client client = clients.get(username);
                if (client.isDeleted())
                    continue;
                out.println(BankUtils.makeParsable(client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveClient(Client client)
    {
        try (PrintWriter out = new PrintWriter(new FileWriter(data, true))) {
            out.println(BankUtils.makeParsable(client));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean createClient(String username, String password, String name, Gender gender, double balance, ClientType type, ClientStatus status)
    {
        if (clients.get(username) != null)
            return false;
        Client client = new Client(username, password, name, gender, type, status);
        saveClient(client);
        clients.put(client.username, client);
        usernames.add(client.username);
        return true;
    }
    
    public void createAccount(String password, String name, Gender gender)
    {
        String n1, n2, n3, n4;
        Random random = new Random();
        n1 = "5055";
        do {
            n2 = "" + random.nextInt(0, 10) + "" + random.nextInt(0, 10) + "" + random.nextInt(0, 10) + "" + random.nextInt(0, 10);
            n3 = "" + random.nextInt(0, 10) + "" + random.nextInt(0, 10) + "" + random.nextInt(0, 10) + "" + random.nextInt(0, 10);
            n4 = "" + random.nextInt(0, 10) + "" + random.nextInt(0, 10) + "" + random.nextInt(0, 10) + "" + random.nextInt(0, 10);
        } while (!createClient(BankUtils.combine("", n1, n2, n3, n4), password, name, gender, 0, ACCOUNT, DEACTIVE));
    }

    public boolean isActive(String username)
    {
        Client client = clients.get(username);
        if (client == null)
            return false;
        return client.status == NORMAL;
    }

    public boolean exists(String username)
    {
        Client client = clients.get(username);
        if (client == null || client.status == DELETE) 
            return false;
        else
            return true;
    }

    public void setType(String username, ClientType type)
    {
        Client client = clients.get(username);
        client.type = type;
    }

    public void makeAdmin(String username)
    {
        setType(username, ADMIN);
    }

    public void makeOperator(String username)
    {
        setType(username, OPERATOR);
    }

    public void makeAccount(String username)
    {
        setType(username, ACCOUNT);
    }

    public void setStatus(String username, ClientStatus status)
    {
        Client client = clients.get(username);
        client.status = status;
    }

    public void delete(String username)
    {
        setStatus(username, DELETE);
    }

    public void deactive(String username)
    {
        setStatus(username, DEACTIVE);
    }

    public void activate(String username)
    {
        setStatus(username, NORMAL);
    }
}
