package top.lovelily.oracle;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Desc: TestClassLoader
 * Author: xuhe
 * Date: 2020/7/21 11:32 上午
 * Version: 1.0
 */
public class TestClassLoader {
    public static void main(String[] args) {
        ClassLoader classLoader = TestClassLoader.class.getClassLoader();
        System.out.println(classLoader.getClass()); // class sun.misc.Launcher$AppClassLoader
        System.out.println(classLoader.getParent().getClass()); // class sun.misc.Launcher$ExtClassLoader
        // System.out.println(classLoader.getParent().getParent().getClass()); // null


        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                    try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1)+".class"; InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name); }
                    byte[] b = new byte[is.available()]; is.read(b);
                    return defineClass(name, b, 0, b.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(name);
            } }
        };
        Object obj = null;
        try {
            obj = myLoader.loadClass("top.lovelily.oracle.TestClassLoader").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(obj.getClass());
        System.out.println(top.lovelily.oracle.TestClassLoader.class.getClassLoader()); // sun.misc.Launcher$AppClassLoader
        System.out.println(obj instanceof top.lovelily.oracle.TestClassLoader);



        try {
            //加载， 并初始化： todo: new, getstatic, putstatic, or invokestatic
            Class cls = Class.forName("top.lovelily.designpattern.singleton.TestEagerMode");  // 初始化： 执行static block, static变量初始化， 普通变量不变
            Field field = cls.getField("a");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

//        try {
//            Class clazz = classLoader.loadClass("top.lovelily.designpattern.singleton.TestEagerMode");
//            //clazz.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
