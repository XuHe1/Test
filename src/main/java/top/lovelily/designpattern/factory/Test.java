package top.lovelily.designpattern.factory;

public class Test {
    public static void main(String[] args) {
        PhoneFactory factory = new PhoneFactory();
        Phone phone = factory.getPhone("iphone");
        phone.make();
    }
}
