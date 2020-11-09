package top.lovelily.designpattern.composite;

/**
 * 抽象出来的公司： 具有相同的功能， 分公司、部门等都是公司的具体实现
 *
 * Desc: Company
 * Author: xuhe
 * Date: 2020/3/29 8:48 下午
 * Version: 1.0
 */
public abstract class Company {
    protected String name;
    public Company(String name) {
        this.name = name;
    }
    protected abstract void add(Company company);
    protected abstract void remove(Company company);
    protected abstract void display(int depth);
    protected abstract void lineOfDuty();
}
