package top.lovelily.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRemoveAll {
    public static void main(String[] args) {
        List<Long> list1 = new ArrayList<>();
        list1.add(1l);
        list1.add(2l);
        list1.add(3l);


        List<Long> list2 = new ArrayList<>();
        list2.add(3l);
        list2.add(5l);
        list2.add(6l);

        boolean b = list1.removeAll(list2);
        System.out.println(b);
        System.out.println(Arrays.toString(list1.toArray()));


    }
}
