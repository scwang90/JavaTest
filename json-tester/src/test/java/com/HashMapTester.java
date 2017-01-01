package com;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * Created by SCWANG on 2016/6/2.
 */
public class HashMapTester {

    @Test
    public void remove() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        map.put("4","4");

        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println(entry.getValue());
            map.remove(entry.getKey());
        }
    }

}
