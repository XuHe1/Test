package top.lovelily.oracle;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc: HeapOOM
 * Author: Xu He
 * created: 2021/10/22 11:29
 * -Xms5m -Xmx5m -XX:+HeapDumpOnOutOfMemoryError
 *
 *
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * oom 只会影响当前的线程，所以即使发生了 oom 其他线程还会继续工作，程序还是正常跑的！！当然元空间列外，元空间OOM 进程压根就起不来。
 */

public class HeapOOM {
    static class OOMObject {
    }
    public static void main(String[] args) {
        // -XX:+UseGCOverheadLimit 不睡眠 225965 睡眠160065
        // -XX:-UseGCOverheadLimit  不睡眠228205  睡眠 177885
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());

            // 线程睡眠，涉及到上下文切换，所以需要等待较长的时间才能看到 OOM
            // todo: 工具看到 gc 其实是在 回收 list 扩容产生的对象? -XX:+PrintGCDetails
//            System.out.println(list.size());
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("销毁了！");
    }
}
