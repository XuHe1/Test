package top.lovelily.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc: ConcreteCompany
 * Author: xuhe
 * Date: 2020/3/29 8:51 下午
 * Version: 1.0
 */
public class ConcreteCompany extends Company {

    private List<Company> children = new ArrayList<>();

    public ConcreteCompany(String name) {
        super(name);
    }

    @Override
    protected void add(Company company) {
        children.add(company);
    }

    @Override
    protected void remove(Company company) {

    }

    @Override
    protected void display(int depth) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < depth; i ++) {
            sb.append("-");
        }
        System.out.println(sb.append(name).toString());

        children.stream().forEach(company -> {
            company.display(depth + 2);
        });
    }

    @Override
    protected void lineOfDuty() {
        children.stream().forEach(company -> {
            company.lineOfDuty();
        });
    }
}
