package top.lovelily;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by XuHe on 17/2/27.
 */

public class User implements Serializable {
    private int id;
    private String name;
    private int age;
    private float height;
    private Address addr;
    public User() {}
    public User(int id, String name){
        this.id = id;
        this.name = name;
    }

    public User(int id, String name, int age, float height){
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("An User instance is finalized");
    }

    public void setAddr(Address address) {
        this.addr = address;
    }


    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", addr=" + addr +
                '}';
    }

    public static void main(String[] args) {
        User user = new User();
        print(user);
    }

    public static void print(Object o) {
        String str = o.toString();
        System.out.println(str);
    }

}
