package top.lovelily.designpattern.proxy;

/**
 * Desc: BookFacadeImpl
 * Author: xuhe
 * Date: 2019/2/22 10:01 PM
 * Version: 1.0
 */
public class MyBookFacade {

    public void addBook(String bookName) {
        System.out.println(String.format("增加图书【%s】普通方法。。。", bookName));
    }
}
