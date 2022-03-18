package top.lovelily.designpattern.template;

/**
 * Desc: Session
 * Author: Xu He
 * created: 2022/3/18 9:59
 */

public abstract class Session {
    public abstract void open();
    public abstract void close();

    public abstract void save();
}
