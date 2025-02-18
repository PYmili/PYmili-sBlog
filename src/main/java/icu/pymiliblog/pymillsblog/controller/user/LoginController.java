package icu.pymiliblog.pymillsblog.controller.user;

import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import icu.pymiliblog.pymillsblog.service.user.LoginService;
import icu.pymiliblog.pymillsblog.service.user.impl.LoginServiceImpl;
import icu.pymiliblog.pymillsblog.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

/**
 * 用户登录 Controller
 * @author PYmili
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问
public class LoginController {

    // 登录的Service
    private final LoginService loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    /**
     * `/user/login` 用户登录接口
     * @param userData {@link UserPojo} 用户数据（json）
     * @param request {@link HttpServletRequest} HTTP Request
     * @return {@link ResponseEntity}
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseCommon> login(
            @RequestBody UserPojo userData,
            HttpServletRequest request) throws NoSuchAlgorithmException {
        log.info("/login: accept request address: " +
                "{}",RequestUtils.getRemoteAddress(request));
        if (userData == null) {
            log.warn("/login: accept data is null.");
            return ApiResponseCommon.not_found("accept data is null.");
        }

        // 用户名或密码为空
        if (userData.getName() == null || userData.getName().isEmpty()
                || userData.getPasswordHash() == null || userData.getPasswordHash().isEmpty())  {
            log.warn("/login: accept username or password is null.");
            return ApiResponseCommon.not_found("用户名或密码为空！");
        }

        return loginService.login(userData);
    }
}
