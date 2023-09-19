package top.lovelily.base;

public class TestLoopControl {

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            if (i==5) {
//                //continue; // 跳过本次，继续向下循环
//                return; // 停止循环
//            }
//            //System.out.println(i);
//        }


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j==5) {
                    //continue; // 跳过本次，继续向下循环
                    return; // 停止循环,退到最外面
                }
                System.out.println( i + ":" + j);
            }

        }
    }
}
