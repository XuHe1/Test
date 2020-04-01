package top.lovelily.tools;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Desc: TestImageBase64
 * Author: xuhe
 * Date: 2019/12/30 4:07 下午
 * Version: 1.0
 */
public class TestImageBase64 {
    public static void main(String[] args) throws IOException {
        File f =  new File("/Users/xuhe/Desktop/plate.jpeg");
        FileInputStream fileInputStreamReader = new FileInputStream(f);
        byte[] bytes = new byte[(int)f.length()];
        fileInputStreamReader.read(bytes);
        String encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
        System.out.println(encodedfile);
        // data:image/jpeg;base64,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
    }

}
