package top.lovelily.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Desc: BookFacadeProxy
 * Author: xuhe
 * Date: 2019/2/22 10:02 PM
 * Version: 1.0
 */
public class BookFacadeProxy implements InvocationHandler {
    private Object target;

    public Object bind(Object target) {
        this.target = target;
        // 被代理类必须实现接口
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                                        target.getClass().getInterfaces(),
                                        this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }

    // jdk动态代理
    public static void main(String[] args) {
        BookFacadeProxy proxy = new BookFacadeProxy();
        System.out.println(proxy);
        BookFacade bookFacade = (BookFacade) proxy.bind(new BookFacadeImpl());
        bookFacade.addBook();
    }
}
