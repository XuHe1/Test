package top.lovelily.designpattern.factory;

/**
 * 只能生产一种产品（对象），比如:华为手机/iphone
 */
public class PhoneFactory {
    public Phone getPhone(String type) {
        if ("iphone".equals(type)) {
            return new Iphone();
        }
        if ("huawei".equals(type)) {
            return new HuaweiPhone();
        }
        return null;
    }
}
