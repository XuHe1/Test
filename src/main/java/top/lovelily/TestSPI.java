package top.lovelily;

import top.lovelily.designpattern.singleton.TestEagerMode;
import top.lovelily.service.Search;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Desc: TestSPI
 * Author: xuhe
 * Date: 2020/6/30 11:00 上午
 * Version: 1.0
 */
public class TestSPI {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // new getStatic invokeStatic putStatic
        TestEagerMode eagerMode = TestEagerMode.getInstance();

        //Class.forName("top.lovelily.designpattern.singleton.TestEagerMode");

        ClassLoader classLoader = TestSPI.class.getClassLoader();
        Class clazz = classLoader.loadClass("top.lovelily.designpattern.singleton.TestEagerMode"); // 不初始化，只是加载到内存
       // clazz.newInstance(); // new, 初始化

        System.out.println("Hello World!");
        ServiceLoader<Search> s = ServiceLoader.load(Search.class);
        Iterator<Search> searchList = s.iterator();
        while (searchList.hasNext()) {
            Search curSearch = searchList.next();
            curSearch.search("test");
        }


    }
}
