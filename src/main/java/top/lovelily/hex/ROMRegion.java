package top.lovelily.hex;

/**
 * Created by admin on 2017/9/25.
 */
public class ROMRegion {
    public final static int REGION_DATA_MAX = 0x40000;

    public long start_address;
    public byte[] data;
    public int data_len;
    public boolean get_first_address;
    public long first_address;

    public ROMRegion() {
        start_address = 0;
        data = new byte[REGION_DATA_MAX];
        data_len = 0;
        get_first_address = false;
        first_address = 0;
    }
}
