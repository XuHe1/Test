package top.lovelily.http;

/**
 * Desc: BusinessException
 * Author: xuhe
 * Date: 2020/4/10 4:22 下午
 * Version: 1.0
 */
public class BusinessException extends RuntimeException {
    private String code;
    private String msg;
    public BusinessException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
