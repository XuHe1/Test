package top.lovelily;

public class UploadMessageHandlerBak implements Runnable {

    public UploadMessageHandlerBak() {
    }


    public void run() {
        int a =  10/0;
        System.out.println(a);

//        try {
//            Thread.sleep(1000);
//            System.out.println(" Handler:: " + Thread.currentThread().getName());
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("catch unknown error!捕获到未知异常！");
//        }
    }
}
