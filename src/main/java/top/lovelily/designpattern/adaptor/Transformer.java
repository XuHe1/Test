package top.lovelily.designpattern.adaptor;

/**
 * Desc: 变压器
 * Author: xuhe
 * Date: 2020/4/4 6:56 下午
 * Version: 1.0
 */
public class Transformer {
    private int input = 220;
    private int output = 5;
    public int transformer(int input) {
        if (input != this.input) {
            return 0;
        }
        // do something...
        System.out.println(input + "V ===>" + output + "V");
        return output;
    }
}
