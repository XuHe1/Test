package top.lovelily.designpattern.factory.absfactory;

/**
 * 超级工厂:工厂的工厂，用来创建工厂
 */
public class SuperFactory {
    public AbstractFactory getFactory(String type) {
        if ("Shape".equals(type)) {
            return new ShapeFactory();
        }
        if ("Color".equals(type)) {
            return new ColorFactory();
        }
        return null;
    }
}
