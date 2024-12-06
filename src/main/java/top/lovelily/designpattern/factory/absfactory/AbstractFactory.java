package top.lovelily.designpattern.factory.absfactory;


import top.lovelily.designpattern.factory.Shape;

/**
 * 抽象工厂：创建多个相关的对象（对象家族）
 */
public abstract class AbstractFactory {
    abstract Shape getShape(String type);
    abstract Color getColor(String color);

}
