package com.blackbank.flyingdollar;

import java.util.ArrayList;
import java.util.HashMap;

public class DataReader {
    private final String source;
    private int start;
    private int current;
    
    public DataReader(String source)
    {
        start = current = 0;
        this.source = source;
    }

    public ArrayList<HashMap<String, String>> parseMaps()
    {
        ArrayList<HashMap<String, String>> maps = new ArrayList<>();
        while (!isAtEnd())
            maps.add(scanMap());
        return maps;
    }

    public HashMap<String, String> scanMap()
    {
        HashMap<String, String> info = new HashMap<>();
        char c;
        String[] field = new String[2];
        int type = 0; //
        while (!isAtEnd() && (c = advance()) != '{' )
            assert c == ' ' || c == '\t' || c == '\n' || c == '\r': "Corrupted source " + source;
        while (!isAtEnd() && (c = advance()) != '}') {
            switch (c) {
                case ' ': case '\n': case '\t': case '\r':
                    break;
                case '\"':
                    start = current-1;
                    readString(field, type);
                    break;
                case ':':
                    type = 1;
                    break;
                case ';':
                    info.put(field[0], field[1]);
                    type = 0;
                    break;
            }
        }

        return info;
    }

    private void readString(String[] field, int type)
    {
        char c = ' ';
        String fieldString = "";
        while (!isAtEnd() && (c = advance()) != '\"') {
            if (c == '\\' && match('\"'))
                fieldString += '\"';
            else
                fieldString += c;
        }
        assert c == '\"': "Unterminated string: " + source.substring(start, current-1);
        field[type] = fieldString;
    }

    private boolean isAtEnd()
    {
        return current >= source.length();
    }
    
    private char advance()
    {
        return source.charAt(current++);
    }
    
    private boolean match(char c)
    {
        if (isAtEnd() || source.charAt(current) != c)
            return false;
        else {
            current++;
            return true;
        }
    }
}
