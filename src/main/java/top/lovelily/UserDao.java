package top.lovelily;

/**
 * Created by kaiitsugyou on 16/12/14.
 */
public class UserDao {
    //系统定义异常，如果不处理，会自动往上抛

    public void save(){

       // int a = 2/0;

        if(2>0){
           // throw new MyException("运行时异常"); //the method need to throws MyException
            throw new MyRunTimeException("运行时异常"); //RuntimeException is an unchecked Exception
        }
    }

    //自定义异常，必须手动往上抛，才能被上层catch
    public void update() throws Exception {
        if(2 > 0){
            throw new Exception("参数异常");
        }
    }
}
