package top.lovelily.designpattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import javax.annotation.Resource;
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
        Object o = enhancer.create();
        return o;
    }
    // 为被代理对象的类生成子类对象， 引申：@Transactional 失效？ private 方法无法被子类重载
    public  Object getInstance(Class clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        Object o = enhancer.create();
        return o;

    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
       // 方法拦截，（对指定方法）做增强操作
        if (method.getName().equals("addBook")) {
            System.out.println("开始添加图书");
            return proxy.invokeSuper(obj, args);
           // return  method.invoke(obj, args);
        }
        return null;
       //
    }

    public static void main(String[] args) {
        MyBookFacade target = new MyBookFacade();
         // top.lovelily.designpattern.proxy.MyBookFacade$$EnhancerByCGLIB$$e6bfbc53
        BookFacadeCglibProxy cglibProxy = new BookFacadeCglibProxy();
        MyBookFacade myBookFacade = (MyBookFacade) cglibProxy.getInstance(target);
        //MyBookFacade myBookFacade = (MyBookFacade) cglibProxy.getInstance(MyBookFacade.class);
        myBookFacade.addBook("《西游记》");
    }

}
