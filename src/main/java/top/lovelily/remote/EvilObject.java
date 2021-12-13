package top.lovelily.remote;

/**
 * Desc: EvilObject
 * Author: Xu He
 * created: 2021/12/13 11:00
 */

public class EvilObject implements Hello {
    static {
        System.out.println("执行恶意代码！");
    }
}
