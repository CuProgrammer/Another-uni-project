package com.blackbank.flyingdollar;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class ChequeManager {
    private HashMap<Integer, Cheque> cheques = new HashMap<>();
    private int IDP;
    private File data;

    public ChequeManager(String dataPath)
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
            Cheque cheque = new Cheque();
            BankUtils.parseMap(cheque, map);
            cheques.put(cheque.chequeId, cheque);
        }
    }

    public void deloadData()
    {
        try (PrintWriter out = new PrintWriter(data)) {
            for (int id = 0; id < IDP; id++) {
                Cheque cheque = cheques.get(id);
                out.println(BankUtils.makeParsable(cheque));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean exists(int id)
    {
        return id < IDP && id >= 0;
    }

    public void setStatus(int id, ChequeStatus status)
    {
        Cheque cheque = cheques.get(id);
        cheque.status = status;
    }
}
