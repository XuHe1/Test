package top.lovelily.designpattern.factory.absfactory;

import top.lovelily.designpattern.factory.Phone;

public class Test {
    public static void main(String[] args) {
        AbstractFactory appleFactory = new AppleFactory();
        Computer computer = appleFactory.createComputer();
        Phone phone = appleFactory.createPhone();
        computer.make();
        phone.make();
    }
}
