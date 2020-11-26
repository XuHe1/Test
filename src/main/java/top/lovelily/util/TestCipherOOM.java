package top.lovelily.util;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.util.Random;

/**
 * Desc: TestCipherOOM
 * Author: xuhe
 * Date: 2020/11/25 下午3:08
 * Version: 1.0
 */
public class TestCipherOOM {

    private static String decrypt(String encryptedStr, PrivateKey privateKey) {
      //  Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
        return null;
    }

    public static void main(String[] args) {
        long adder = 0xBL; // 0xB
        System.out.println(adder);

        long multipler = 0x5DEECE66DL;
        System.out.println(multipler);

        long seed = 20l;

        long newSeed = (seed * multipler + adder) & ((1l << 48) -1);

        System.out.println((int)(newSeed >>> (48 - 32)));

        Random random = new Random(4324324L);
        System.out.println(random.nextInt());





    }
}
