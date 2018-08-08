package com.revature.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParser {
	
	public static Map<String,String> parse(String json) {
        json = json.replace('{', ' ').replace('}',' ').trim();

        String[] kvpairs = json.split(",");
        Map<String, String> kvmap = new HashMap<String,String>();
        for(String kv : kvpairs) {
        	System.out.println(kv);
            String[] entries = kv.split(":");
            String key = entries[0].replace('\"', ' ').trim();
            String value = entries[1].replace('\"', ' ').trim();
            kvmap.put(key, value);
        }
        return kvmap;
    }
	
	public static List<Map<String,String>> parseArray(String json) {
        json = json.replace('[', ' ').replace(']', ' ').trim();
        
        int firstBracer = 0;
        int secondBracer = 0;
        String object;
        List<Map<String,String>> mappedJson = new ArrayList<>();
        do {
            if(firstBracer > json.length() || secondBracer > json.length()) break;
            firstBracer = json.indexOf("{", secondBracer);
            secondBracer = json.indexOf("}", firstBracer) + 1;
            object = json.substring(firstBracer, secondBracer);
            mappedJson.add(parse(object));
            firstBracer = secondBracer + 2;
        } while(firstBracer != -1);

        return mappedJson;
		
	}
}
