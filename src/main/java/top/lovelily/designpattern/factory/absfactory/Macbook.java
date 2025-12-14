package top.lovelily.designpattern.factory.absfactory;

/**
 * Desc: Macbook
 * Author: xuhe
 * Date: 2025/12/14 8:20 下午
 * Version: 1.0
 */
public class Macbook implements Computer{
    @Override
    public void make() {
        System.out.println("Macbook pro");
    }
}
