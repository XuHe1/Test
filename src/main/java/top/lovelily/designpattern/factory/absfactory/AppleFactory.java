package top.lovelily.designpattern.factory.absfactory;


import top.lovelily.designpattern.factory.Iphone;
import top.lovelily.designpattern.factory.Phone;

public class AppleFactory extends AbstractFactory {

    @Override
    Phone createPhone() {
        return new Iphone();
    }

    @Override
    Computer createComputer() {
        return new Macbook();
    }
}
