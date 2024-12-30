package icu.pymiliblog.pymillsblog.controller.user;

import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.UserPojo;
import icu.pymiliblog.pymillsblog.service.user.UserInfoService;
import lombok.extern.flogger.Flogger;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/user_info")
    public ResponseEntity<ResultPojo> userInfo(@RequestBody UserPojo userPojo) {
        if (userPojo == null) {
            logger.warn("request body is null!");
            return ResultPojo.not_found("request body is null!");
        } else if (userPojo.getName() == null || userPojo.getToken() == null) {
            logger.warn("name or token is null!");
            return ResultPojo.not_found("name or token is null!");
        }
        return userInfoService.userInfo(userPojo);
    }
}
