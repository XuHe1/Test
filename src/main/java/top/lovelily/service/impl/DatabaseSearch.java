package top.lovelily.service.impl;

import top.lovelily.service.Search;

import java.util.List;

/**
 * Desc: DatabaseSearch
 * Author: xuhe
 * Date: 2020/6/30 10:58 上午
 * Version: 1.0
 */
public class DatabaseSearch implements Search {
    @Override
    public List<String> search(String keyword) {
        System.out.println("now use database search. keyword:" + keyword);
        return null;
    }

}
