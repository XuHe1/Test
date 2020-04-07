package top.lovelily.designpattern.adaptor;

/**
 * Desc: MobilePowerAdaptor
 * Author: xuhe
 * Date: 2020/4/4 6:10 下午
 * Version: 1.0
 *
 * 家用电源
 */
public class MobilePowerAdaptor extends Power {
    // 输出电压
    private int output = 5;

    private Transformer transformer = new Transformer();

    public boolean supports(Object o) {
        return o instanceof Mobile;
    }

    public String supply() {

        int  output = transformer.transformer(super.getVoltage());
        if (output == this.output) {
            return "当前电压：" + output + "V";
        }

        return "电源保护，自动断开！";

    }

}
