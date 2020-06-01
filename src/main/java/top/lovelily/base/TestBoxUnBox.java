package top.lovelily.base;

import top.lovelily.User;

/**
 * Desc: TestBoxUnBox
 * Author: xuhe
 * Date: 2019/9/19 10:25 AM
 * Version: 1.0
 */
public class TestBoxUnBox {

    // 注意： 由于model里是基本类型， 参数里是引用类型， 实际项目中，如果参数optional就会出现NPE
    public void updateUser(Integer id) {
        User user = new User(1, "xuhe");
        user.setId(id);
    }
    private static  Integer a = new Integer(0);

    public static void main(String[] args) {
//        TestBoxUnBox test = new TestBoxUnBox();
//        test.updateUser(null);
//

        System.out.println(Integer.valueOf(0) == a);
    }
}
