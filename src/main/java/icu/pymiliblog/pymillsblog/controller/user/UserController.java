package icu.pymiliblog.pymillsblog.controller.user;

import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.service.user.UserService;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verify")
    public ResponseEntity<ResultPojo> verify(@RequestHeader(value = "Authentication") String authHeader) {
        log.info("/login: request values: " +
                "Authentication Header: {}", authHeader);
        // 验证jwt
        if (JwtUtils.VerifyJwtIsValid(authHeader)) {
            log.warn("/login: Illegal request.");
            return ResultPojo.IllegalRequest();
        }
        return ResultPojo.ok("success");
    }
}
