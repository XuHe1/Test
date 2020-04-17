package top.lovelily.util;

/**
 * Desc: Define
 * Author: xuhe
 * Date: 2020/2/20 3:59 下午
 * Version: 1.0
 */
public final class Define {
    private Define() {}

    // 常用 0 长度数组定义
    public static final byte[] ZERO_BYTES = new byte[0];
    public static final short[] ZERO_SHORTS = new short[0];
    public static final int[] ZERO_INTS = new int[0];
    public static final long[] ZERO_LONGS = new long[0];
    public static final float[] ZERO_FLOATS = new float[0];
    public static final double[] ZERO_DOUBLES= new double[0];
    public static final Object[] ZERO_OBJECTS = new Object[0];
    public static final String[] ZERO_STRINGS = new String[0];

    // KB, MB, GB 单位定义
    public static final int KB = (1<<10);
    public static final int MB = (1<<20);
    public static final int GB = (1<<30);
    public static final long TB = (1L<<40);

    // 空字符串
    public static final String EMPTY_STRING = "";


    // 处理器核数
    public static final int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors();
}
