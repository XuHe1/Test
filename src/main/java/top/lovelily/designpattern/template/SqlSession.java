package top.lovelily.designpattern.template;

/**
 * Desc: SqlSession
 * Author: Xu He
 * created: 2022/3/18 10:02
 */

public class SqlSession extends Session{
    @Override
    public void open() {
        System.out.println("open hibernate session");
    }

    @Override
    public void close() {
        System.out.println("close hibernate session");
    }

    @Override
    public void save() {
        System.out.println("hibernate session save");
    }
}
