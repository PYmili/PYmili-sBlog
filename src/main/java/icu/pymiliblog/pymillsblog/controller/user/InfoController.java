package icu.pymiliblog.pymillsblog.controller.user;

import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.service.user.InfoService;
import icu.pymiliblog.pymillsblog.service.user.impl.InfoServiceImpl;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息 Controller
 * @author PYmili
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问
public class InfoController {

    // 用户信息的Service
    private final InfoService infoService;

    InfoController(InfoServiceImpl infoService) {
        this.infoService = infoService;
    }

    /**
     * 验证JWT接口
     * @param authHeader {@link String}
     * @return {@link ResponseEntity}
     */
    @GetMapping("/verify")
    public ResponseEntity<ApiResponseCommon> verify(@RequestHeader(value = "Authentication") String authHeader) {
        log.info("/login: request values: " +
                "Authentication Header: {}", authHeader);
        // 验证jwt
        if (JwtUtils.VerifyJwtIsValid(authHeader)) {
            log.warn("/login: Illegal request.");
            return ApiResponseCommon.IllegalRequest();
        }
        return ApiResponseCommon.ok("success");
    }
}
