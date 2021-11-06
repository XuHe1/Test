package top.lovelily.function;

/**
 * Desc: Test
 * Author: Xu He
 * created: 2021/11/2 17:09
 */

public class Test {
    public void invoke(InvocationCallback invocationCallback) {
        invocationCallback.proceedWithInvocation();
    }

    public static void main(String[] args) {
        MethodInvocation method = new MethodInvocation();

        Test t = new Test();
        t.invoke(method::proceed);
    }
}
