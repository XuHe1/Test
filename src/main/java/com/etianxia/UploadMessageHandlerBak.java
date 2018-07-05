package com.etianxia;

public class UploadMessageHandlerBak implements Runnable {

    public UploadMessageHandlerBak() {
    }


    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(" Handler:: " + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch unknown error!捕获到未知异常！");
        }
    }
}
