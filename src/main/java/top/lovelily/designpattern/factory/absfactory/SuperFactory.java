package top.lovelily.designpattern.factory.absfactory;

/**
 * 超级工厂:工厂的工厂，用来创建某个具体的工厂
 */
public class SuperFactory {
    public static AbstractFactory getFactory(String type) {
        if ("Apple".equals(type)) {
            return new AppleFactory();
        }
        if ("Huawei".equals(type)) {
            return new HuaweiFactory();
        }
        return null;
    }
}
