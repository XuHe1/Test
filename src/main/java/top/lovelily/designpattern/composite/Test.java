package top.lovelily.designpattern.composite;

/**
 * Desc: Test
 * Author: xuhe
 * Date: 2020/3/29 9:24 下午
 * Version: 1.0
 */
public class Test {
    public static void main(String[] args) {
        // 公司=分公司、部门
        ConcreteCompany root = new ConcreteCompany("北京总公司");
        root.add(new HRDepartment("总公司人力资源部"));
        root.add(new FinanceDepartment("总公司财务部"));

        // 分公司
        ConcreteCompany company = new ConcreteCompany("上海华东分公司");
        company.add(new HRDepartment("华东分公司人力资源部"));
        company.add(new HRDepartment("华东分公司财务部"));

        // 办事处
        ConcreteCompany hzOffice = new ConcreteCompany("杭州办事处");
        hzOffice.add(new HRDepartment("杭州办事处人力资源部"));
        hzOffice.add(new FinanceDepartment("杭州办事处财务部"));

        ConcreteCompany hfOffice = new ConcreteCompany("合肥办事处");
        hfOffice.add(new HRDepartment("合肥办事处人力资源部"));
        hfOffice.add(new FinanceDepartment("合肥办事处财务部"));

        company.add(hzOffice);
        company.add(hfOffice);
        root.add(company);

        root.display(1);
        System.out.println("\n");
        root.lineOfDuty();


    }
}
