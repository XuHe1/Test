package top.lovelily.designpattern.composite;

/**
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
