package top.lovelily.base;

public final class FinalClass {
    private  int num = 0;
    private final  int num1;

    public FinalClass(int num1) {
        this.num1 = num1;
    }


    public static void main(String[] args) {
        // final 类的非 final 属性可以修改
        FinalClass f = new FinalClass(2);
        f.num = 2;
        System.out.println(f.num);
        // final 类的 final 属性必须显示初始化或者构造方法初始化，一旦初始化后不能再变！！！！
        //f.num1 = 3;
    }
}

// final class 不能被继承
//class Child extends FinalClass {
//
//}
