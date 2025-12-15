package top.lovelily.base;

public class TestTry {
    public static void main(String[] args) {
        try {
            //int a = 1/0;
            return; // 执行 finally
        } catch (Exception e) {
            //throw e; // 执行 finally
          //  return; // 执行 finally
        } finally {
            System.out.println("finally");
        }
    }
}
