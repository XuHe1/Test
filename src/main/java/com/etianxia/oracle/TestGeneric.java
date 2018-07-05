package com.etianxia.oracle;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author h.xu
 * @create 2018-02-05 下午3:16
 **/

public class TestGeneric<T> {
    private T[] e;
    private Collection<T> c;

    // static method
    public static <T> void fromArrayToCollection(T [] e, Collection<T> c) {

    }

    // non-static method
    public void fromArrayToCollection1(T [] e, Collection<T> c) {

    }


    // wildcards
    public  void fromArrayToCollection2(T e , Collection<? extends T> c) {
      //  c.add(e);
    }

    public  void fromArrayToCollection3(T e , Collection<? super T> c) {
        c.add(e);
    }

    public static void main(String[] args) {
        TestGeneric<Number> testGeneric = new TestGeneric<>();
        testGeneric.fromArrayToCollection3(new Integer(1), new ArrayList<Number>());


    }


}
