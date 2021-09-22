package top.lovelily.designpattern.proxy;

/**
 * Desc: BookFacadeImpl
 * Author: xuhe
 * Date: 2019/2/22 10:01 PM
 * Version: 1.0
 */
public class MyBookFacade {
    private String bookName;

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void addBook() {
        System.out.println(String.format("增加图书【%s】普通方法。。。", this.bookName));
    }
}
