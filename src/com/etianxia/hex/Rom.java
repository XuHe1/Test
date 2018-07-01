package com.etianxia.hex;

import java.util.List;

/**
 * Created by admin on 2017/9/25.
 */
public class Rom {
    public final static int ROM_PROGRAM_MAX = 0x25800;

    public int program_len;                //程序长度
    public long program_start_address;    //程序起始地址
    public byte[] program_data;            //程序数据
    public int program_crc;            //程序CRC校验和
    public int program_frame_count;        //程序帧数量
    public int param_len;                //参数长度
    public long param_start_address;        //参数起始地址
    public byte[] param_data;            //参数数据
    public int param_crc;                //参数CRC校验和
    public int param_frame_count;        //参数帧数量
    public int software_ver;            //软件版本
    public int device_fact_id;            //厂商编号
    public int device_type;                //设备类型

    public List<Frame> frames;

    public Rom() {
        program_data = new byte[ROM_PROGRAM_MAX];
        param_data = new byte[ROM_PROGRAM_MAX];
    }
}
