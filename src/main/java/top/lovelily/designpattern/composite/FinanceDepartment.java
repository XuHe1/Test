package top.lovelily.designpattern.composite;

/**
 * Desc: FinanceDepartment
 * Author: xuhe
 * Date: 2020/3/29 9:11 下午
 * Version: 1.0
 */
public class FinanceDepartment extends Company {
    public FinanceDepartment(String name) {
        super(name);
    }

    @Override
    protected void add(Company company) {

    }

    @Override
    protected void remove(Company company) {

    }

    @Override
    protected void display(int depth) {
        StringBuffer sb = new StringBuffer();
        while (depth > 0) {
            sb.append("-");
            depth --;
        }

        System.out.println(sb.append(name).toString());
    }

    @Override
    protected void lineOfDuty() {
        System.out.println(name + " 财务管理与工资发放");
    }
}
