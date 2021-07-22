package error;

import java.io.Serializable;

public class ErrorCode implements Serializable {
    private int code;
    private String info;

    public ErrorCode(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
