package top.lovelily.hex;

/**
 * Created by admin on 2017/9/25.
 */
public class Frame {
    public final static  int ROM_FRAME_SIZE=2048;

    public int frame_index;
    public long frame_addr_offset;
    public int frame_crc;
    public int frame_len;
    public int package_count;
    public short package_size;
    public byte[] data;

    public Frame()
    {
        data=new byte[ROM_FRAME_SIZE];
    }
}
