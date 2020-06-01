package top.lovelily.designpattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Desc: BookFacadeCglibProxy
 * Author: xuhe
 * Date: 2019/2/22 10:18 PM
 * Version: 1.0
 */
public class BookFacadeCglibProxy implements MethodInterceptor {
    private Object target;

    public  Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public  Object getInstance(Class clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return proxy.invokeSuper(obj, args);
    }

    public static void main(String[] args) {
        BookFacadeCglibProxy cglibProxy = new BookFacadeCglibProxy();
        // MyBookFacade myBookFacade = (MyBookFacade) cglibProxy.getInstance(new MyBookFacade());
        MyBookFacade myBookFacade = (MyBookFacade) cglibProxy.getInstance(MyBookFacade.class);
        myBookFacade.addBook();
    }

}
