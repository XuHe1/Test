package top.lovelily.designpattern.factory;

public class Iphone implements Phone {
    @Override
    public void make() {
        System.out.println("an iphone");
    }
}
