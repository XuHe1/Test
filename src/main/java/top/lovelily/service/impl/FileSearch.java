package top.lovelily.service.impl;

import top.lovelily.service.Search;

import java.util.List;

/**
 * Desc: FileSearch
 * Author: xuhe
 * Date: 2020/6/30 10:59 上午
 * Version: 1.0
 */
public class FileSearch implements Search {
    @Override
    public List<String> search(String keyword) {
        System.out.println("now use file system search. keyword:" + keyword);
        return null;
    }

}
