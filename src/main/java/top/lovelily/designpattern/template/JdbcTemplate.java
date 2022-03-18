package top.lovelily.designpattern.template;

/**
 * Desc: template
 * Author: Xu He
 * created: 2022/3/18 9:57
 */

public abstract class JdbcTemplate {
    // 父类定义操作（骨架）
    public final void save() {
        Session session = getSession();
        session.open();
        session.save();
        session.close();

    }
    // 子类定义具体实现
    public abstract Session getSession();
}
