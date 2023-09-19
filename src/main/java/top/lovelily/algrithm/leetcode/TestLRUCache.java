package top.lovelily.concurrent;

import java.util.*;

public class TestLRUCache {
    LinkedHashMap<String, String> cache = new LinkedHashMap(10, 0.75f, true){
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return cache.size()>10;
        }
    };

    public void put(String key, String value) {


    }
    public static void main(String[] args) {

    }

}
