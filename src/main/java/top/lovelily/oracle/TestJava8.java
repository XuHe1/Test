package top.lovelily.oracle;

import top.lovelily.User;

import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author h.xu
 * @create 2018-01-30 下午1:50
 **/
public class TestJava8 {
    private AtomicInteger count = new AtomicInteger(0); // AtomicInteger内部实现使用了volatile
    private LongAdder count1;
    private DateTimeFormatter formatter;
    public int getCount() {
        ConcurrentHashMap<String,LongAdder> freqs = new ConcurrentHashMap<>(); //CAS(put), Lock() 使用了分段锁，多个分段，每个分段一把锁 segments[]
        //freqs.computeIfAbsent("c"->new LongAdder().increment());
        return count.getAndIncrement();
    }


    private static String getVal() {

        return "compute value";
    }

    private static int dividedBy(int a) {
        return 100/a;
    }


    /**
     *
     *        Optional.ofNullable(str).ifPresent(t -> {
     *             // do something with the non-null str
     *         });
     *
     *
     *         Optional.ofNullable(str).map().filter().orElseThrow()
     *         Optional.ofNullable(str).map().filter().orElseGet()
     *
     *
     *         numList.stream().filter(c -> c > 0).map(c -> dividedBy(c)).collect(Collectors.toList());
     */

    public static void main(String[] args) {
        User u = new User(1, "xuhe");
        User u2 = u;
        System.out.println(u == u2);
        u2.setName("xuhe1");
        System.out.println(u.getName());

        String str = null;
        Optional<String> optional = Optional.ofNullable(str);
        System.out.println(optional.isPresent());  // 用来检查 NPE
        optional.ifPresent(new Consumer<String>() {  // todo: Consumer接口可以new??
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });


        Optional.ofNullable(str).ifPresent(t -> {
            // do something with the non-null str
        });




        /**
         *
         * https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
         *
         * lambda express:   anonymous classes with single method
         * passed as an argument to another method
         * treat functionality as method argument, or code as data
         *
         */
        optional.ifPresent(s -> System.out.println(s));


        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };


        // lambda: no arg
        Thread thread = new Thread(() -> System.out.println(11));



        Movable movable = new Movable() {
            @Override
            public void move() {
                System.out.println("move ....");
            }
        };

        movable.move();

        optional.ifPresent(value -> System.out.println(value));

        // test Optional
        Optional<String> stringOptional = Optional.empty();
        System.out.println(stringOptional.isPresent()); // false


        str = stringOptional.orElse("default");
        System.out.println(str);

        String str1 = stringOptional.orElseGet(()-> getVal());
        System.out.println(str1);

        String string = null;
        stringOptional = Optional.ofNullable(string); // => of()  empty()

        str = stringOptional.orElse("default1");
        System.out.println(str);

        // ok
        Integer intNum = null;
        Optional<Integer> op = Optional.ofNullable(intNum).filter(n -> n == 1);
        if (op.isPresent()) {
            System.out.println(op.get());
        }

        stringOptional = Optional.of("hello");
        System.out.println(stringOptional.get());
        List<String> list = new ArrayList<>();
        stringOptional.ifPresent(value -> list.add(value));
        System.out.println(list.size());

        // collection forEach lambda
        list.forEach(s -> {
            System.out.println(s);
        });


        Collections.sort(list, (s1, s2) -> s1.compareTo(s2));

        Comparator.comparing(User::getId).thenComparing(User::getName);

        // test collection stream()
        Map<String, Class> annotatedClass =  new HashMap<>();
        annotatedClass.put("Integer", Integer.class);

        List<Integer> numList = new ArrayList<>();
//        numList.add(2);
//        numList.add(4);
//        numList.add(8);
        // numList.add(0);
        if (numList != null) {
            numList = numList.stream().filter(c -> c > 0).map(c -> dividedBy(c)).collect(Collectors.toList());
            numList.stream().forEach(num -> System.out.println(num));
        }



        annotatedClass.values().stream().forEach(handler -> {

            try {
                System.out.println(handler.getName());
                Method method = handler.getMethod("valueOf", String.class, int.class);
                Integer obj = (Integer) method.invoke(null, "16", 16);
                System.out.println(obj);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Class cls = new Integer(2).getClass();
        try {
            Method method1  = cls.getMethod("getSamplingTime");
            System.out.println(method1 == null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }

}
