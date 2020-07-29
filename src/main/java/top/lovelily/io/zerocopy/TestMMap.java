package top.lovelily.io.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Desc: TestMMap
 * Author: xuhe
 * Date: 2020/7/24 11:14 下午
 * Version: 1.0
 */
public class TestMMap {
    public static void main(String[] args) {
        try {
            FileInputStream input = new FileInputStream("/Users/xuhe/IdeaProject/Test/src/main/java/top/lovelily/io/zerocopy/1.txt");
            FileChannel channel = input.getChannel();
            MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            System.out.println(Charset.forName("utf-8").decode(mappedBuffer).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
