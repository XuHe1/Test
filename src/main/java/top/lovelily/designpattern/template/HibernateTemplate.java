package top.lovelily.designpattern.template;

/**
 * Desc: HibernateTemplate
 * Author: Xu He
 * created: 2022/3/18 10:01
 */

public class HibernateTemplate extends JdbcTemplate{
    @Override
    public Session getSession() {
        return new SqlSession();
    }
}
