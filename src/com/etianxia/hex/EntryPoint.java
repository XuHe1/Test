package com.etianxia.hex;

import java.util.Scanner;

/**
 * @author h.xu
 * @create 2017-12-08 下午5:15
 **/

public class EntryPoint {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String arg = scanner.next();
        // 旧版本
        if ("0".equals(arg)) {
                HEXUtil hexUtil = new HEXUtil();
                hexUtil.ParseFile("/Users/kaiitsugyou/Desktop/xx.hex");
        }
        // 新版本
        if ("1".equals(arg)) {
              HEXUtil2 hexUtil2 = new HEXUtil2();
              hexUtil2.ParseFile("/Users/kaiitsugyou/Desktop/wlcan.hex");
        }
    }
}
