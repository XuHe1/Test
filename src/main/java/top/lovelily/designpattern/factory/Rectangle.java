package top.lovelily.designpattern.factory;

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("drawing a Rectangle");
    }
}
