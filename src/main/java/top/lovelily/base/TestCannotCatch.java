package top.lovelily.base;

public class TestCannotCatch {
    public static void main(String[] args) {

        try{
            try{
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000l);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int a = 2/0;
                    }
                });

                t.start();


            } finally {
                System.out.println("a");
            }
        } catch (Exception e) {
            // 捕获不了异步线程中的异常，所以使用线程、线程池一定要捕捉异常
            System.out.println("异常！" + e);
        }



    }
}
