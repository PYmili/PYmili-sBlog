package icu.pymiliblog.pymillsblog.pojo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResultPojo {
    private int code;
    private Object data;

    public ResultPojo(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    // Fail error Response
    public static final ResponseEntity<ResultPojo> failError = new ResponseEntity<>(
            new ResultPojo(HttpStatus.NOT_FOUND.value(), "fail"),
            HttpStatus.NOT_FOUND
    );

    public static ResponseEntity<ResultPojo> ok(Object body) {
        return new ResponseEntity<>(
                new ResultPojo(HttpStatus.OK.value(), body),
                HttpStatus.OK
        );
    }

    public static ResponseEntity<ResultPojo> not_found(String result) {
        return new ResponseEntity<>(
                new ResultPojo(HttpStatus.NOT_FOUND.value(), result),
                HttpStatus.NOT_FOUND
        );
    }

    public static ResponseEntity<ResultPojo> IllegalRequest() {
        return new ResponseEntity<>(
                new ResultPojo(404, "非法请求"),
                HttpStatus.NOT_FOUND
        );
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
