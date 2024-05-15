package top.lovelily.designpattern.factory.absfactory;


import top.lovelily.designpattern.factory.Shape;

public class ColorFactory extends AbstractFactory {
    @Override
    Shape getShape(String type) {
        return null;
    }

    @Override
    Color getColor(String color) {
        if ("Green".equals(color)) {
            return new Green();
        }
        if ("Blud".equals(color)) {
            return new Blue();
        }
        return null;
    }
}
