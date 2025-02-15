package icu.pymiliblog.pymillsblog.controller.user;

import icu.pymiliblog.pymillsblog.common.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import icu.pymiliblog.pymillsblog.service.user.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/reg")
    public ResponseEntity<ResultPojo> register(@RequestBody UserPojo requestBody) {
        if (requestBody == null) {
            log.warn("/user/reg: 参数残缺！");
            return ResultPojo.not_found("参数残缺！");
        }
        String username = requestBody.getName();
        String passwordHash = requestBody.getPasswordHash();
        if (username == null || username.isEmpty()
                || passwordHash == null || passwordHash.isEmpty()) {
            log.warn("/user/reg: 注册用户数据残缺！request body: {}", requestBody);
            return ResultPojo.not_found("参数残缺！");
        }
        return registerService.register(requestBody);
    }
}
