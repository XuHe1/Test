package top.lovelily.oracle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author h.xu
 * @create 2018-02-05 下午3:16
 **/

public class TestGeneric<T> {
    private T[] e;
    private Collection<T> c;

    // static method
    public static <K> void fromArrayToCollection(K [] e, Collection<K> c) {

    }

    // non-static method
    public void fromArrayToCollection1(T [] e, Collection<T> c) {

    }

    /**
     *  PECS
     *  Producer: write, Extends
     *  Consumer: read, Super
     *
     * <? extends T>允许调用读方法T get()获取T的引用，但不允许调用写方法set(T)传入T的引用（传入null除外）；
     *
     * <? super T>允许调用写方法set(T)传入T的引用，但不允许调用读方法T get()获取T的引用（获取Object除外）。
     *
     */

    // wildcards <? extends T>方法参数类型只能读取不能写入
    public  void fromArrayToCollection2(T e , Collection<? extends T> c) {
       // c.add(e);
       e = c.iterator().next();
    }

    public  void fromArrayToCollection3(T e , Collection<? super T> c) {
        c.add(e);
       // e = c.iterator().next();
    }

    public static void main(String[] args) {
        TestGeneric<Number> testGeneric = new TestGeneric<>();
        testGeneric.fromArrayToCollection3(new Integer(1), new ArrayList<Number>());
        List<String> list = new ArrayList<>();
        list.stream().findFirst().orElse("");

    }


}
