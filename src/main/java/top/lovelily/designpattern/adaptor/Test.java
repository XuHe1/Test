package top.lovelily.designpattern.adaptor;

/**
 * Desc: Test
 * Author: xuhe
 * Date: 2020/4/4 6:21 下午
 * Version: 1.0
 */
public class Test {
    public static void main(String[] args) {
        // 家庭用的交流电已经有了，可以用来给家电直接使用，
        // 现在需要给手机充电，Power的不满足手机5V要求，同时又不适合对Power做修改
        // 手机电源适配器出来了
        Mobile mobile = new Mobile();
        Power power = new MobilePowerAdaptor();
        if (power.supports(mobile)) {
            String p = power.supply();
            System.out.println(p);
        }
    }
}
