package icu.pymiliblog.pymillsblog.controller.user;

import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.UserPojo;
import icu.pymiliblog.pymillsblog.service.user.LoginService;
import icu.pymiliblog.pymillsblog.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    /**
     * `/user/login` 用户登录接口
     * @param userData 用户数据（json）
     * @param request HTTP Request
     * @return ResponseEntity<ResultPojo>
     */
    @PostMapping("/login")
    public ResponseEntity<ResultPojo> login(@RequestBody UserPojo userData, HttpServletRequest request) throws NoSuchAlgorithmException {
        logger.info("/login accept request address: {}", RequestUtils.getRemoteAddress(request));
        if (userData == null) {
            logger.warn("/login accept data is null.");
            return ResultPojo.not_found("accept data is null.");
        }
        // 用户名或密码为空
        if (userData.getName().isEmpty() || userData.getPassword().isEmpty())  {
            logger.warn("/login accept username or password is null.");
            return ResultPojo.not_found("用户名或密码为空！");
        }

        return loginService.login(userData, logger);
    }
}
