package icu.pymiliblog.pymillsblog.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * api接口返回封装
 * @author PYmili
 */
public class ApiResponseCommon {
    private int code;
    private String msg;
    private Object data;

    public ApiResponseCommon(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiResponseCommon(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    /**
     * Fail error Response - 返回操作失败
     */
    public static final ResponseEntity<ApiResponseCommon> failError = new ResponseEntity<>(
            new ApiResponseCommon(HttpStatus.NOT_FOUND.value(), "fail", null),
            HttpStatus.NOT_FOUND
    );

    /**
     * 返回成功
     * @param body {@link Object}
     * @return {@link ResponseEntity}
     */
    public static ResponseEntity<ApiResponseCommon> ok(Object body) {
        return new ResponseEntity<>(
                new ApiResponseCommon(HttpStatus.OK.value(), "success", body),
                HttpStatus.OK
        );
    }

    /**
     * 返回未找到
     * @param result {@link String}
     * @return {@link ResponseEntity}
     */
    public static ResponseEntity<ApiResponseCommon> not_found(String result) {
        return new ResponseEntity<>(
                new ApiResponseCommon(HttpStatus.NOT_FOUND.value(), "Not found", result),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * 非法请求
     * @return {@link ResponseEntity}
     */
    public static ResponseEntity<ApiResponseCommon> IllegalRequest() {
        return new ResponseEntity<>(
                new ApiResponseCommon(404, "Illegal", null),
                HttpStatus.NOT_FOUND
        );
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
