package top.lovelily;

/**
 * Desc: TestExamChinTel 中电信量子笔试
 * Author: xuhe
 * Date: 2024/3/12 10:52 下午
 * Version: 1.0
 */
public class TestExamChinTel {
    // try  必须要一个 catch 或 finally
    // super()必须在第一行， this()?
    // （匿名）内部类不能定义 static 方法和变量？
    // interface 方法定义
    // float f = 1.1; double d = 2.15e12;
    // 泛型的优点？类型安全：编译时就能做类型检查，减少了运行时类型转换异常。2. 不需要类型转换：运行时无需类型转换。3. 运行快：可以避免运行时类型转换，运行更快。省去装箱拆箱？
    // 反射能获取私有变量和方法吗？
    // 先执行 try 中的return 还是 finally 中先打印
    // ReentrantLock 是互斥锁吗？
    // lamda 只能定义一个抽象方法，函数接口
    // 抽象工厂是对工厂模式的抽象吗？
    // Thread.stop()会导致线程休眠？
    // 数字开头5个数字：^\d{5}    ^[\d]{5}

    int sum(int a, int b) {
        try{
            return a + b;
        } catch (Exception e) {
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");
        }
        return 0;
    }

    public static void main(String[] args) {
//        try {
//            System.out.println("hello");
//        } //'catch' or 'finally' expected

        TestExamChinTel test = new TestExamChinTel();
        System.out.println("和是" + test.sum(1,2)); // 先打印 finally block 再返回
        double d = 2.15e12;

    }






}

class  A{
    A() {}
}

class B extends A{
    int a ;
    B() {
        super();// 必须在第一行
        int a =1;
    }
    B(int a) {
        // super() 和 this() 不可能同时出现
        super();
        //this();
    }

    interface  C  {
        void a();
        public double b();
        protected int c();
        abstract int d();
        final int e();
        static void f(){};
    }

    /**
     * protected: 子类可以访问，子类和父类在不同包；
     *
     */
}
