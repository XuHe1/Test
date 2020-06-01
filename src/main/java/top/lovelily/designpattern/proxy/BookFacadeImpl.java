package top.lovelily.designpattern.proxy;

/**
 * Desc: BookFacadeImpl
 * Author: xuhe
 * Date: 2019/2/22 10:01 PM
 * Version: 1.0
 */
public class BookFacadeImpl implements BookFacade {

    @Override
    public void addBook() {
        System.out.println("增加图书方法。。。");
    }
}
