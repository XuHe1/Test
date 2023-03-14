package top.lovelily.oracle;

/**
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseParNewGC -XX:+PrintGCDetails -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15   -XX:+PrintTenuringDistribution
 */
public class TestDynamicAging {
    private static final int _1MB = 1024 * 1024;
    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB / 4]; // allocation1+allocation2大于survivor空间一半    10 * 1/10 = 1 M
        allocation2 = new byte[_1MB / 4];  // 3%
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB]; // 触发 minor gc
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }
}
