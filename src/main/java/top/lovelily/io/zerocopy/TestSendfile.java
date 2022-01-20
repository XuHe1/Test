package top.lovelily.io.zerocopy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Desc: TestSendfile 零拷贝
 * Author: xuhe
 * Date: 2020/7/24 11:10 下午
 * Version: 1.0
 */
public class TestSendfile {
    public static void main(String[] args) {
        try {
            FileInputStream input = new FileInputStream("/Users/xuhe/IdeaProject/Test/src/main/java/top/lovelily/io/zerocopy/1.txt");
            FileChannel channel = input.getChannel();
            FileOutputStream out = new FileOutputStream("/Users/xuhe/IdeaProject/Test/src/main/java/top/lovelily/io/zerocopy/2.txt");
            channel.transferTo(0, channel.size(), out.getChannel());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
