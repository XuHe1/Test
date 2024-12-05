package top.lovelily.designpattern.factory.absfactory;

public class Test {
    public static void main(String[] args) {
        AbstractFactory factory = SuperFactory.getFactory("Shape");
        factory.getShape("Circle");

        AbstractFactory factory2 = SuperFactory.getFactory("Color");
        factory2.getColor("Green");
    }
}
