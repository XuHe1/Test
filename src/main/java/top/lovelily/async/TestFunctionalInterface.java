package top.lovelily.async;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TestFunctionalInterface<T> {

    T value;
    public void accept(Consumer<? super T> action) {
        action.accept(value);
    }

    public static void main(String[] args) {
        TestFunctionalInterface<String> test = new TestFunctionalInterface<>();
        test.value = "test";

        test.accept(s -> {
            System.out.println(s);
        });

        List<String> list = new ArrayList<String>();
        list.stream().forEach(s -> {

         }

        );
    }
}
