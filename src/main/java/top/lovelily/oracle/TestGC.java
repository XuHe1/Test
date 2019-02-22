package top.lovelily.oracle;

/**
 * Desc: TestGC
 * Author: xuhe
 * Date: 2019/2/15 11:06 AM
 * Version: 1.0
 */
public class TestGC {
    private static final int _1MB = 1024 * 1024;
    public static void main(String[] args) {
        /**
         *  -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
         *
         *  按照"空间分配担保"策略，申请alloc4时，eden已经分配了6M，剩余2M，执行minor GC，
         *  eden复制到survivor区，survivor区内存只有1M,因此直接进入老年代， 执行完了，eden区和survivor被清空，alloc4分配在eden区:
         *  eden: 4m    survivor: 0   old: 6M
         *
         *  todo: PS收集器为了保证吞吐量 并且在新生代没有足够的内存的情况下 直接把新的大对象分配到了老年代
         *  eden: 6M   survivor: 0   old: 4M
         *
         *
         */

            byte[] alloc1, alloc2, alloc3, alloc4;
            alloc1 = new byte[2 * _1MB];
            alloc2 = new byte[2 * _1MB];
            alloc3 = new byte[2 * _1MB];
            alloc4 = new byte[3 * _1MB];


    }
}
