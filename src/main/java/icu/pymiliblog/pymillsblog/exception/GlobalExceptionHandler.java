package icu.pymiliblog.pymillsblog.exception;

import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author PYmili
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 异常捕捉器
     * @param ex {@link Exception}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseCommon> exceptionHandler(Exception ex) {
      log.info("Global exception handler: {}", ex.toString());
      return ApiResponseCommon.not_found("操作失败！服务器错误！");
    }

}
