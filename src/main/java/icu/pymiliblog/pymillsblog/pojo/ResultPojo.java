package icu.pymiliblog.pymillsblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultPojo {
    private int code;
    private String msg;
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

    public static ResponseEntity<ResultPojo> not_found(String result) {
        return new ResponseEntity<>(
                new ResultPojo(HttpStatus.NOT_FOUND.value(), result),
                HttpStatus.NOT_FOUND
        );
    }

}
