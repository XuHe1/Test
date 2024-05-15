package top.lovelily.designpattern.factory.absfactory;


import top.lovelily.designpattern.factory.Shape;

public abstract class AbstractFactory {
    abstract Shape getShape(String type);
    abstract Color getColor(String color);

}
