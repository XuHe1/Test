package top.lovelily;

import java.io.Serializable;

/**
 * Created by XuHe on 17/2/27.
 */
public class User implements Serializable {
    private int id;
    private String name;
    private int age;
    private float height;
    private String addr;
    public User(int id, String name){
        this.id = id;
        this.name = name;
    }

    public User(int id, String name, int age, float height, String addr){
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.addr = addr;
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
}
