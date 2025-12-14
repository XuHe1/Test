package top.lovelily.designpattern.factory;

public class HuaweiPhone implements Phone {
    @Override
    public void make() {
        System.out.println("Huawei phone");
    }
}
