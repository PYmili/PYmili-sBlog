package icu.pymiliblog.pymillsblog.controller.user;

import icu.pymiliblog.pymillsblog.common.ResultPojo;
import icu.pymiliblog.pymillsblog.service.user.InfoService;
import icu.pymiliblog.pymillsblog.service.user.impl.InfoServiceImpl;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问
public class InfoController {

    private InfoService infoService;

    InfoController(InfoServiceImpl infoService) {
        this.infoService = infoService;
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
