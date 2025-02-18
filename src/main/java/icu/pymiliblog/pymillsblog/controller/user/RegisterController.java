package icu.pymiliblog.pymillsblog.controller.user;

import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import icu.pymiliblog.pymillsblog.service.user.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户注册 Controller
 * @author PYmili
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问
public class RegisterController {

    // 注册的Service
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /**
     * 用户注册接口
     * @param requestBody {@link UserPojo}
     * @return {@link ResponseEntity}
     */
    @PostMapping("/reg")
    public ResponseEntity<ApiResponseCommon> register(@RequestBody UserPojo requestBody) {
        if (requestBody == null) {
            log.warn("/user/reg: 参数残缺！");
            return ApiResponseCommon.not_found("参数残缺！");
        }
        String username = requestBody.getName();
        String passwordHash = requestBody.getPasswordHash();
        if (username == null || username.isEmpty()
                || passwordHash == null || passwordHash.isEmpty()) {
            log.warn("/user/reg: 注册用户数据残缺！request body: {}", requestBody);
            return ApiResponseCommon.not_found("参数残缺！");
        }
        return registerService.register(requestBody);
    }
}
