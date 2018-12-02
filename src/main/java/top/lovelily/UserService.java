package top.lovelily;

/**
 * Created by kaiitsugyou on 16/12/14.
 */
public class UserService {
    private UserDao userDao = new UserDao();
    public void add(){
        userDao.save();
    }

    public void update() throws Exception {
        try {
            userDao.update();
        } catch (Exception e) {
            throw  e;
        }
    }
}
