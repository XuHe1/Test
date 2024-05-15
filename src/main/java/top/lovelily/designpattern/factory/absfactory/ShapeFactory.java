package top.lovelily.designpattern.factory.absfactory;


import top.lovelily.designpattern.factory.Circle;
import top.lovelily.designpattern.factory.Rectangle;
import top.lovelily.designpattern.factory.Shape;

public class ShapeFactory extends AbstractFactory {


    @Override
    public Shape getShape(String type) {
        if ("Rectangle".equals(type)) {
            return new Rectangle();
        }
        if ("Circle".equals(type)) {
            return new Circle();
        }
        return null;
    }

    @Override
    Color getColor(String color) {
        return null;
    }
}
