package top.lovelily.exception;

/**
 *
 * 终止方法：
 *      1. return
 *      2. throw new Exception
 *
 * Created by xuhe on 2018/5/12.
 */
public class TestException {
    private static final int count; // 常量，只能被初始化一次，定义时初始化，或静态代码里初始化
    private static int a;

    static {
        a = 3;
       count = 0;
    }

    public  TestException() {
        a = 4 ;
        // count = 1;
    }

    private abstract static class Inner {
        public abstract void test();
        //public abstract static void test1(); // 错误 abstract 不能和static一起修饰方法
    }



    public int multiply() {
        try {
            int a = 10 / 1;
            System.out.println(a);

            return 1;

        } catch (Exception e) {
            return -1;
        } finally {
            System.out.println("finally");
            return 0;  // finally里的return先于 try 和 catch里的 return
        }
    }


    public static void main(String[] args) throws Exception {
        TestException testException = new TestException();
        System.out.println(testException.a);

        try {

            int a = 10/1;
            //return; // 先执行finally，再执行return，所以如果finally里有return，本行就不再执行。
        } catch (Exception e) {
            System.out.println(e);
            throw e;
          //  return; // catch 后程序会继续往下走，所以如果是致命性异常，需要return， 或者手动抛出去
        } finally {
            // 不管前面有没有return， 都会执行
            System.out.println("finally");
            // return; // 后续不再执行
        }
        System.out.println("hello");


        TestException test = new TestException();
        System.out.println(test.multiply());


        while (true) {
            try {
                int b = 10/0;
                System.out.println(b);
            } catch (Exception e) {
                e.printStackTrace();
                throw e; // 跳出循环, 不catch 也会跳出循环
            }

        }




    }
}
