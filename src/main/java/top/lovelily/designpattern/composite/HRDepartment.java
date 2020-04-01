package top.lovelily.designpattern.composite;


/**
 * Desc: HRDepartment
 * Author: xuhe
 * Date: 2020/3/29 9:10 下午
 * Version: 1.0
 */
public class HRDepartment extends Company {
    public HRDepartment(String name) {
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
        System.out.println(name + " 人力资源招聘与培训");
    }
}
