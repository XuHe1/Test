package top.lovelily.designpattern.adaptor;

/**
 * Desc: 民用交流电
 * Author: xuhe
 * Date: 2020/4/4 6:16 下午
 * Version: 1.0
 */

public class Power {
    private int voltage = 220;

    public int getVoltage() {
        return voltage;
    }

    public boolean supports(Object o) {
        return o instanceof Abbreviation;
    }
    // 供电
    public String supply() {
        return "当前电压：" + voltage + " V";
    }
}
