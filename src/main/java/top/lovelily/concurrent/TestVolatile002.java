package top.lovelily.concurrent;

import java.util.concurrent.TimeUnit;

/**
 *
 * -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -Xcomp -XX:CompileCommand=dontinline,*TestVolatile001.setIsStop
 * Desc: TestVolatile001
 * Author: xuhe
 * Date: 2020/7/7 10:04 上午
 * Version: 1.0
 */
public class TestVolatile002 {

    private volatile boolean  isStop = false;

    //private  boolean  isStop = false;

    public boolean isIsStop() {
        return isStop;
    }

    public void setIsStop(boolean isStop) {
        this.isStop = isStop;
    }


    public static void main(String[] args) {
        TestVolatile002 t1 = new TestVolatile002();
        // 时间先后
        new Thread(() ->{
            try {
                TimeUnit.SECONDS.sleep(1);
                t1.setIsStop(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            try {
                //TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(t1.isIsStop());
        }).start();


    }

}
