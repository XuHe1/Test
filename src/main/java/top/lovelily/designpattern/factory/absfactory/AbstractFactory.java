package top.lovelily.designpattern.factory.absfactory;


import top.lovelily.designpattern.factory.Phone;

/**
 * 抽象工厂：生产同一族（同公司不同的）商品，如苹果公司
 */
public abstract class AbstractFactory {
    abstract Phone createPhone();
    abstract Computer createComputer();

}
