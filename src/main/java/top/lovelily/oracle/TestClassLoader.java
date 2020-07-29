package top.lovelily.oracle;

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
    }
}
