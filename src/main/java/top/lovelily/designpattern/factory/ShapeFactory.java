package top.lovelily.designpattern.factory;

/**
 * 只能生产一种产品（对象），比如”形状“
 */
public class ShapeFactory {
    public factory.Shape getShape(String type) {
        if ("Rectangle".equals(type)) {
            return new factory.Rectangle();
        }
        if ("Circle".equals(type)) {
            return new Circle();
        }
        return null;
    }
}
