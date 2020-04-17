package top.lovelily.util;

import io.netty.util.internal.StringUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 *  包含 short, int 的基于字节和位的相关操作
 * Author: xuhe
 * Date: 2020/2/20 3:56 下午
 * Version: 1.0
 */

public final class ByteUtil {
    private ByteUtil() {}

    public static final String HEX_STRING = "0123456789ABCDEF";
    static final char[] HEX_ARRAY = HEX_STRING.toCharArray();

    public static final int LEN_OF_BYTE = 1;
    public static final int LEN_OF_SHORT = 2;
    public static final int LEN_OF_INT = 4;
    public static final int LEN_OF_LONG = 8;

    /**
     * byte 转换为 int, 不作符号扩展
     */
    public static int ubyte2Int(byte d) {
        return d & 0xFF;
    }

    /**
     * byte 转换为 short, 不作符号扩展
     */
    public static short ubyte2Short(byte d) {
        return (short)(d & 0xFF);
    }

    /**
     * 将 bytes 中的 1 个字节视为 ubyte/uint1 转化为 int (LITTLE_ENDIAN)
     */
    public static int uint1_2int(byte[] bytes, int offset) {
        return bytes[offset] & 0xFF;
    }

    /**
     * 将 bytes 中的 1 个字节视为 byte/sint1 转化为 int (LITTLE_ENDIAN)
     */
    public static int sint1_2int(byte[] bytes, int offset) {
        return bytes[offset];
    }

    /**
     * 将 bytes 中的 2 个字节视为 ushort/uint2 转化为 int (LITTLE_ENDIAN)
     */
    public static int uint2_2int(byte[] bytes, int offset) {
        return (bytes[offset] & 0xFF) | ((bytes[offset+1] & 0xFF) << 8);
    }

    /**
     * 将 bytes 中的 2 个字节视为 short/sint2 转化为 int (LITTLE_ENDIAN)
     */
    public static int sint2_2int(byte[] bytes, int offset) {
        return (short) uint2_2int(bytes, offset);
    }

    /**
     * 将 bytes(LITTLE_ENDIAN) 中的 4 个字节视为 int4 转化为 int
     */
    public static int int4_2int(byte[] bytes, int offset) {
        return (bytes[offset] & 0xFF)
                | ((bytes[offset+1] & 0xFF) << 8)
                | ((bytes[offset+2] & 0xFF) << 16)
                | ((bytes[offset+3] & 0xFF) << 24)
                ;
    }

    /**
     * 将 bytes(BIG_ENDIAN) 中的 4 个字节视为 int4 转化为 int
     */
    public static int int4_2intB(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF) << 24)
                | ((bytes[offset+1] & 0xFF) << 16)
                | ((bytes[offset+2] & 0xFF) << 8)
                | (bytes[offset+3] & 0xFF)
                ;
    }

    /**
     * short 转换为 int, 不作符号扩展
     */
    public static int ushort2Int(short d) {
        return d & 0xFFFF;
    }

    /**
     * int 转换为 long, 不作符号扩展
     */
    public static long uint2long(int d) {
        return d & 0xFFFFFFFFL;
    }

    /**
     * 将 int 转化为 byte 数组 (BIG_ENDIAN)
     */
    public static byte[] ints2bytes(int... args) {
        byte[] ret = new byte[args.length * 4];
        int pos = 0;
        for (int d : args) {
            ret[pos++] = (byte)((d & 0xFF000000) >> 24);
            ret[pos++] = (byte)((d & 0xFF0000) >> 16);
            ret[pos++] = (byte)((d & 0xFF00) >> 8);
            ret[pos++] = (byte)(d & 0xFF);
        }
        return ret;
    }

    /**
     * @see #bytes2ints(byte[], int, int)
     */
    public static int[] bytes2ints(byte[] bytes) {
        return bytes2ints(bytes, 0, bytes.length);
    }

    /**
     * 将 byte 数组转化为 int 数组 (BIG_ENDIAN)
     * 如果 len 长度不是 4 的倍数, 最后 < 4 个字节会被忽略掉
     */
    public static int[] bytes2ints(byte[] bytes, int offset, int len) {
        int[] ret = new int[len / LEN_OF_INT];
        int pos = offset, tmp;
        for (int i = 0; i < ret.length; ++i) {
            tmp = (bytes[pos++] & 0xFF) << 24;
            tmp |= (bytes[pos++] & 0xFF) << 16;
            tmp |= (bytes[pos++] & 0xFF) << 8;
            tmp |= (bytes[pos++] & 0xFF);
            ret[i] = tmp;
        }
        return ret;
    }

    /**
     * 将 short 转化为 byte 数组 (BIG_ENDIAN)
     */
    public static byte[] shorts2bytes(short... args) {
        byte[] ret = new byte[args.length * 2];
        int pos = 0;
        for (short d : args) {
            ret[pos++] = (byte)((d & 0xFF00) >> 8);
            ret[pos++] = (byte)(d & 0xFF);
        }
        return ret;
    }

    /**
     * 将 byte 数组转化为 short 数组 (BIG_ENDIAN)
     * 如果 len 长度不是 2 的倍数, 最后 < 2 个字节会被忽略掉
     */
    public static short[] bytes2shorts(byte[] bytes, int offset, int len) {
        short[] ret = new short[len / LEN_OF_SHORT];
        int pos = offset;
        short tmp;
        for (int i = 0; i < ret.length; ++i) {
            tmp = (short) ((bytes[pos++] & 0xFF)<< 8);
            tmp |= bytes[pos++] & 0xFF;
            ret[i] = tmp;
        }
        return ret;
    }

    /**
     * 将字符串转化为固定长度的字节数组
     * 如果字符串长度超出长度限制会截断, 如果不够会以空格填充
     * 参数不作检测, 调用者自己保证, 否则抛异常
     */
    public static byte[] string2bytes(String s, int len) {
        if (s == null) {
            s = "";
        }
        byte[] sb = s.getBytes(Charset.defaultCharset());
        if (sb.length == len) {
            return sb;
        }
        byte[] ret = new byte[len];
        int count = sb.length < len ? sb.length : len;
        System.arraycopy(sb, 0, ret, 0, count);
        for (int i = count; i < len; ++i) {
            ret[i] = (byte)' ';
        }
        return ret;
    }

    /**
     * 将 short 序列转换成十六进制字符串(BIG_ENDIAN)
     */
    public static String shorts2Hex(short... shorts) {
        if(shorts == null){
            return Define.EMPTY_STRING;
        }
        StringBuilder sb = new StringBuilder(shorts.length * 4);
        for (short d : shorts) {
            sb.append(HEX_ARRAY[(d & 0xf000) >> 12]);
            sb.append(HEX_ARRAY[(d & 0x0f00) >> 8]);
            sb.append(HEX_ARRAY[(d & 0x00f0) >> 4]);
            sb.append(HEX_ARRAY[(d & 0x000f)]);
        }
        return sb.toString();
    }

    /**
     * 将 int 序列转换成十六进制字符串(BIG_ENDIAN)
     */
    public static String ints2Hex(int... ints) {
        return bytes2Hex(ints2bytes(ints));
    }

    /**
     * @see #bytes2Hex(int, int, byte...)
     */
    public static String bytes2Hex(byte... bytes) {
        if(bytes == null){
            return Define.EMPTY_STRING;
        }
        return bytes2Hex(0, bytes.length, bytes);
    }

    /**
     * 将缓冲内的可读的数据转化为 16 进制字符串.
     * 本操作不会对缓冲区的指针作任何修改.
     * @param bb 要求缓冲为可读模式. 允许为只读缓冲区.
     */
    public static String bytes2Hex(ByteBuffer bb) {
        if (bb == null) {
            return Define.EMPTY_STRING;
        }
        if (bb.isReadOnly()) {
            int oldPos = bb.position();
            byte[] data = new byte[bb.remaining()];
            bb.get(data).position(oldPos);
            return bytes2Hex(data);
        }
        return bytes2Hex(bb.position(), bb.limit(), bb.array());
    }


    /**
     * 将字节序列转换成十六进制字符串
     * 不作参数, 下表等的检测
     * @return 转换后的字符串长度
     */
    public static String bytes2Hex(int offset, int len, byte... bytes) {
        if(bytes == null){
            return Define.EMPTY_STRING;
        }
        StringBuilder sb = new StringBuilder(len * 2);
        byte b;
        for (int i = 0; i < len; ++i) {
            b = bytes[i+offset];
            sb.append(HEX_ARRAY[(b & 0xF0) >> 4]);
            sb.append(HEX_ARRAY[(b & 0x0F)]);
        }
        return sb.toString();
    }

    /**
     * 将十六进制字符串转换为字节数组
     * 4>todo: 如果字符串错误, 返回错误数据需要抛异常吧? 目前中文字符等错误字符会被转化为 FF
     * @param hex 十六进制字符串
     * @return 如果成功转换, 返回结果字节数组. 如果字符串格式错误或者为 null 返回空数组
     */
    public static byte[] hex2Bytes(String hex) {
        if(StringUtil.isNullOrEmpty(hex)) {
            return Define.ZERO_BYTES;
        }
        char[] hexChars = hex.toCharArray();
        byte[] result = new byte[(hexChars.length + 1) / 2];
        int i = 0;
        if (hexChars.length % 2 != 0) {
            i = 1;
            result[0] = (byte) Character.digit(hexChars[0], 16);
        }
        while (i < hexChars.length) {
            int high = Character.digit(hexChars[i++], 16);
            int low = Character.digit(hexChars[i], 16);
            result[i>>1] = (byte) ((high << 4) | low);
            ++i;
        }
        return result;
    }

    /**
     * 从 {@link ByteBuffer} 读取字节数组, 只是简化了数组创建和读取操作, 减少几行代码.
     * 注意调用的时
     * @param bb 处于 "读" 模式的 {@link ByteBuffer} 对象
     * @param len 需要读取的长度(函数内部不会对数据长度进行检验)
     * @return 成功读取的字节数组
     */
    public static byte[] getBytesFromBB(ByteBuffer bb, int len) {
        byte[] data = new byte[len];
        bb.get(data);
        return data;
    }

    /**
     * 基于 HEX(半个字节....) 的反转义
     * 适用于 APA 协议, 转义规则特殊, 对于其他类似 '\' 的转义规则可以写成通用的
     * 5E7D -> 7E, 5E5D -> 5E
     * 实现上, 5EXX -> 5E, 5E$ -> 5E, 这些是转义写错了, 本函数不进行纠正和错误提示
     * 4>care: 这是为了匹配原先部分设备的转义方式实现的转义方法. 基于半个字节进行转义的.
     *      比如数据 05E7D0 和 05E5D0 也会被替换. (注意大小写问题?)
     *      注意这个转换存在一些条件限制, 很容易出现意外问题, 而且要注意替换顺序.
     * 4>: 此转义实现方法可以认为是错误的, 目前基本没有设备还采用这种转义了.
     */
    public static byte[] antiEscape4ApByHex(byte[] bytes, int offset, int len) {
        if (bytes == null || bytes.length == 0){
            return Define.ZERO_BYTES;
        }
        String hex = bytes2Hex(offset, len, bytes);
        hex = hex.replace("5E7D", "7E");
        hex = hex.replace("5E5D", "5E");
        return hex2Bytes(hex);
    }

    /**
     * 基于 HEX(半个字节....) 的转义.
     * 为了匹配原先部分设备的转义方式而实现.
     * 注意这个转换存在一些条件限制, 很容易出现意外问题, 而且要注意替换顺序.
     * 4>: 此转义实现方法可以认为是错误的, 目前基本没有设备还采用这种转义了.
     * @return 新创建的经过转义的字节数组. 如果源数据为空或者长度为 0 返回 byte[0]
     */
    public static byte[] escape4ApByHex(byte[] bytes, int offset, int len) {
        if (bytes == null || bytes.length == 0) {
            return Define.ZERO_BYTES;
        }
        String hex = bytes2Hex(bytes);
        String repHex = hex.substring(offset*2, offset*2 + len*2).replace("5E", "5E5D").replace("7E", "5E7D");
        return hex2Bytes(hex.substring(0, offset*2) + repHex + hex.substring(offset*2+len*2, hex.length()));
    }

    /**
     * 基于字节的反转义
     * 适用于 APD 协议, 转义规则特殊, 对于其他类似 '\' 的转义规则可以写成通用的
     * 5E7D -> 7E, 5E5D -> 5E
     * 实现上, 5EXX -> 5E, 5E$ -> 5E, 这些是转义写错了, 本函数不进行纠正和错误提示
     * 4>todo: 考虑是否需要提供返回非转义区域的数据
     * @param bytes 转义了的字节数组
     * @return 反转义后的字节数组, 只返回参数制定范围的字符数组转以后的结果, 其余部分不包含在返回结果内
     */
    public static byte[] antiEscape4ApByByte(byte[] bytes, int offset, int len) {
        if (bytes == null || len == 0) {
            return Define.ZERO_BYTES;
        }
        byte[] result = new byte[len];
        byte bPre, bNow;
        int rstLen = 0, pos;
        for (pos = offset+1; pos < offset + len; ++pos) {
            bPre = bytes[pos-1];
            if (bPre == (byte)0x5E) {
                bNow = bytes[pos];
                ++pos;
                if (bNow == (byte)0x5D) {
                    result[rstLen++] = (byte)0x5E;
                } else if (bNow == (byte)0x7D) {
                    result[rstLen++] = (byte)0x7E;
                } else {
                    --pos;
                    result[rstLen++] = bPre;
                }
            } else {
                result[rstLen++] = bPre;
            }
        }
        if (pos == offset + len) {
            result[rstLen++] = bytes[pos-1];
        }
        if (rstLen != len) {
            result = Arrays.copyOf(result, rstLen);
        }
        return result;
    }

    /**
     * 转义字节数组,  适用于 APD 协议
     */
    public static byte[] escape4ApByByte(byte[] bytes) {
        return escape4ApByByte(bytes, 0, bytes.length);
    }

    /**
     * 转义字节数组,  适用于 APD 协议
     * 7E -> 5E7D, 5E -> 5E5D
     * @param offset 开始转义偏移位置
     * @param len 需要转义的字节长度
     * @return  如果参数为 null 返回 byte[0]
     *          如果正常转义, 返回一个新的数组, 包换原数组未转义和转以后的所有数据
     */
    public static byte[] escape4ApByByte(byte[] bytes, int offset, int len) {
        if (bytes == null) {
            return Define.ZERO_BYTES;
        }
        byte b;
        int count = 0;
        for (int i = offset; i - offset < len; ++i) {
            b = bytes[i];
            if (b == (byte)0x7E || b == (byte)0x5E) {
                ++count;
            }
        }
        if (count == 0) {
            return bytes.clone();
        }
        byte[] ret = new byte[bytes.length + count];
        int pos = offset;
        System.arraycopy(bytes, 0, ret, 0, offset);
        for (int i = offset; i - offset < len; ++i) {
            b = bytes[i];
            if (b == (byte)0x7E) {
                ret[pos++] = (byte)0x5E;
                ret[pos++] = (byte)0x7D;
            } else if (b == (byte)0x5E) {
                ret[pos++] = (byte)0x5E;
                ret[pos++] = (byte)0x5D;
            } else {
                ret[pos++] = b;
            }
        }
        System.arraycopy(bytes, offset+len, ret, pos, bytes.length - len - offset);
        return ret;
    }


    private static final int[] CRC_16_TABLE = { 0x0000, 0x1021, 0x2042,
            0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7, 0x8108, 0x9129, 0xa14a,
            0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef, 0x1231, 0x0210, 0x3273,
            0x2252, 0x52b5, 0x4294, 0x72f7, 0x62d6, 0x9339, 0x8318, 0xb37b,
            0xa35a, 0xd3bd, 0xc39c, 0xf3ff, 0xe3de, 0x2462, 0x3443, 0x0420,
            0x1401, 0x64e6, 0x74c7, 0x44a4, 0x5485, 0xa56a, 0xb54b, 0x8528,
            0x9509, 0xe5ee, 0xf5cf, 0xc5ac, 0xd58d, 0x3653, 0x2672, 0x1611,
            0x0630, 0x76d7, 0x66f6, 0x5695, 0x46b4, 0xb75b, 0xa77a, 0x9719,
            0x8738, 0xf7df, 0xe7fe, 0xd79d, 0xc7bc, 0x48c4, 0x58e5, 0x6886,
            0x78a7, 0x0840, 0x1861, 0x2802, 0x3823, 0xc9cc, 0xd9ed, 0xe98e,
            0xf9af, 0x8948, 0x9969, 0xa90a, 0xb92b, 0x5af5, 0x4ad4, 0x7ab7,
            0x6a96, 0x1a71, 0x0a50, 0x3a33, 0x2a12, 0xdbfd, 0xcbdc, 0xfbbf,
            0xeb9e, 0x9b79, 0x8b58, 0xbb3b, 0xab1a, 0x6ca6, 0x7c87, 0x4ce4,
            0x5cc5, 0x2c22, 0x3c03, 0x0c60, 0x1c41, 0xedae, 0xfd8f, 0xcdec,
            0xddcd, 0xad2a, 0xbd0b, 0x8d68, 0x9d49, 0x7e97, 0x6eb6, 0x5ed5,
            0x4ef4, 0x3e13, 0x2e32, 0x1e51, 0x0e70, 0xff9f, 0xefbe, 0xdfdd,
            0xcffc, 0xbf1b, 0xaf3a, 0x9f59, 0x8f78, 0x9188, 0x81a9, 0xb1ca,
            0xa1eb, 0xd10c, 0xc12d, 0xf14e, 0xe16f, 0x1080, 0x00a1, 0x30c2,
            0x20e3, 0x5004, 0x4025, 0x7046, 0x6067, 0x83b9, 0x9398, 0xa3fb,
            0xb3da, 0xc33d, 0xd31c, 0xe37f, 0xf35e, 0x02b1, 0x1290, 0x22f3,
            0x32d2, 0x4235, 0x5214, 0x6277, 0x7256, 0xb5ea, 0xa5cb, 0x95a8,
            0x8589, 0xf56e, 0xe54f, 0xd52c, 0xc50d, 0x34e2, 0x24c3, 0x14a0,
            0x0481, 0x7466, 0x6447, 0x5424, 0x4405, 0xa7db, 0xb7fa, 0x8799,
            0x97b8, 0xe75f, 0xf77e, 0xc71d, 0xd73c, 0x26d3, 0x36f2, 0x0691,
            0x16b0, 0x6657, 0x7676, 0x4615, 0x5634, 0xd94c, 0xc96d, 0xf90e,
            0xe92f, 0x99c8, 0x89e9, 0xb98a, 0xa9ab, 0x5844, 0x4865, 0x7806,
            0x6827, 0x18c0, 0x08e1, 0x3882, 0x28a3, 0xcb7d, 0xdb5c, 0xeb3f,
            0xfb1e, 0x8bf9, 0x9bd8, 0xabbb, 0xbb9a, 0x4a75, 0x5a54, 0x6a37,
            0x7a16, 0x0af1, 0x1ad0, 0x2ab3, 0x3a92, 0xfd2e, 0xed0f, 0xdd6c,
            0xcd4d, 0xbdaa, 0xad8b, 0x9de8, 0x8dc9, 0x7c26, 0x6c07, 0x5c64,
            0x4c45, 0x3ca2, 0x2c83, 0x1ce0, 0x0cc1, 0xef1f, 0xff3e, 0xcf5d,
            0xdf7c, 0xaf9b, 0xbfba, 0x8fd9, 0x9ff8, 0x6e17, 0x7e36, 0x4e55,
            0x5e74, 0x2e93, 0x3eb2, 0x0ed1, 0x1ef0 };

    /**
     * @see ByteUtil#getCrc16(byte[], int, int)
     */
    public static short getCrc16(byte[] data) {
        return getCrc16(data, 0, data.length);
    }

    public static short getCrc16(byte[] data, int offset) {
        return getCrc16(data, offset, data.length - offset);
    }

    /**
     * 返回 crc 16 结果, 直接沿用原 proto 的算法
     * 不对数据作严格检查, 如果参数无效会产生异常, 需要调用者自己保证数据有效性
     * @return 如果正常计算返回 crc 校验值, 如果数据为空或者计算数据长度为 0 返回 0
     */
    public static short getCrc16(byte[] data, int offset, int len) {
        int crc = 0, idx;
        short da;
        for (int i = 0; i < len; ++i) {
            da = (short)(crc / 256);
            crc <<= 0x08;
            idx = da ^ data[i + offset];
            idx = idx % 256;
            if (idx < 0) {
                idx += 256;
            }
            crc ^= CRC_16_TABLE[idx];
            crc &= 0xFFFF;
        }
        return (short)crc;
    }
}

