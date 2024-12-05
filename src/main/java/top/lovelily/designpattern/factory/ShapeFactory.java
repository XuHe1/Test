package top.lovelily.designpattern.factory;

/**
 * 简单工厂模式
 * 只能生产一种产品（对象），比如”形状“
 * 对客户端隐藏具体对象（Shape）的子类
 */
public class ShapeFactory {
    public Shape getShape(String type) {
        if ("Rectangle".equals(type)) {
            return new Rectangle();
        }
        if ("Circle".equals(type)) {
            return new Circle();
        }
        return null;
    }
}
