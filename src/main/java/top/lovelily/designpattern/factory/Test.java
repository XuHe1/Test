package top.lovelily.designpattern.factory;

public class Test {
    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();
        Shape circle = factory.getShape("Circle");
        circle.draw();
    }
}
