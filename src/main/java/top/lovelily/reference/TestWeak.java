package top.lovelily.reference;

import top.lovelily.User;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Desc: TestWeak
 * 弱引用包装的对象一旦发生gc,会被立刻回收
 * Author: xuhe
 * Date: 2020/7/10 2:53 下午
 * Version: 1.0
 */
class B {

}

class A {
    private B b;
    private WeakReference<B> bWeakReference;
    public A(B b) {
        //this.b = b;
        bWeakReference = new WeakReference<>(b);
    }

    public B getB() {
        //return b;
        return bWeakReference.get();
    }
}


public class TestWeak {



    public static void main(String[] args) {

        User user = new User();

        Map<User, String> map = new HashMap<>();
        map.put(user, "hello");


        WeakReference<User> userWeakReference = new WeakReference<>(user);
        System.out.println(userWeakReference.get());
        System.gc();
        System.out.println(userWeakReference.get());

        B b = new B();
        A a = new A(b);
        b = null;
        System.gc();
        System.out.println(a.getB());

    }
}
