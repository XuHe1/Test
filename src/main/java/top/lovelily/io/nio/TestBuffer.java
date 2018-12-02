package top.lovelily.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Desc: https://docs.oracle.com/javase/7/docs/api/java/nio/ByteBuffer.html
 * Author: xuhe
 * Date: 2018/9/18 上午11:02
 * Version: 1.0
 */
public class TestBuffer {
    static final int BUFFER_SIZE = 1024;

    /**
     * 普通读取
     *
     */
    public void testRead() throws Exception {
        File file = new File("F:\\aa.pdf");
        FileInputStream in = new FileInputStream(file);
        FileChannel channel = in.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // IOUtil 堆外内存--> 堆内内存
        channel.read(buffer);

        MappedByteBuffer buff = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                channel.size());

        byte[] b = new byte[1024];
        int len = (int) file.length();

        long begin = System.currentTimeMillis();

        for (int offset = 0; offset < len; offset += 1024) {

            if (len - offset > BUFFER_SIZE) {
                buff.get(b);
            } else {
                buff.get(new byte[len - offset]);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("time is:" + (end - begin));

    }

    /**
     * 堆外内存
     * 内存映射文件
     * @throws Exception
     */
    public void testMemoryMapping() throws Exception {
        File file = new File("F:\\aa.pdf");
        FileInputStream in = new FileInputStream(file);
        FileChannel channel = in.getChannel();
        MappedByteBuffer buff = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                channel.size());

        byte[] b = new byte[1024];
        int len = (int) file.length();

        long begin = System.currentTimeMillis();

        for (int offset = 0; offset < len; offset += 1024) {

            if (len - offset > BUFFER_SIZE) {
                buff.get(b);
            } else {
                buff.get(new byte[len - offset]);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("time is:" + (end - begin));

    }

    public static void main(String[] args) {
        try {
            Thread.sleep(20000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * jconsole
         *
         * java堆外内存(native 堆， full gc才回收）
         * 默认大小和heap相同
         * native堆：
         *    1. 属于jvm进程
         *    2. 执行full gc会被回收
         *    3. 使用的是用户空间，由于内核具有较高的权限，故可以直接访问
         *
         * java -Xms100m -Xmx100m  com/etianxia/io/nio/TestBuffer
         * java.lang.OutOfMemoryError: Direct buffer memory
         *
         * java -Xms100m -Xmx100m  -XX:MaxDirectMemorySize=900M  com/etianxia/io/nio/TestBuffer
         *
         *  java -Xms10240m -Xmx10240m  com/etianxia/io/nio/TestBuffer 堆使用的虚拟地址，64位进程分配内存大于物理内存，jvm官方推荐使用物理内存的1/4
         */
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024*1024*1024*10);

        // java堆内存
        ByteBuffer.allocate(1024*1024*50);
        while (true) {

        }


    }
}
