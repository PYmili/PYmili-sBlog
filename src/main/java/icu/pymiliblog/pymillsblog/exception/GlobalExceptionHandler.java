package icu.pymiliblog.pymillsblog.exception;

import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultPojo> exceptionHandler(Exception ex) {
      log.info("Global exception handler: {}", ex.toString());
      return ResultPojo.not_found("操作失败！服务器错误！");
    }
}
