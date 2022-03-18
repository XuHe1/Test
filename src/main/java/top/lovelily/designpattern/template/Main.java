package top.lovelily.designpattern.template;

/**
 * Desc: Main
 * Author: Xu He
 * created: 2022/3/18 10:03
 */

public class Main {
    public static void main(String[] args) {
        JdbcTemplate template = new HibernateTemplate();
        template.save();
    }
}
