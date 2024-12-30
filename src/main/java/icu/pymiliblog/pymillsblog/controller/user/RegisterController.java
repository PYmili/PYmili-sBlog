package icu.pymiliblog.pymillsblog.controller.user;

import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.UserPojo;
import icu.pymiliblog.pymillsblog.service.user.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;

    @PostMapping("/reg")
    public ResponseEntity<ResultPojo> register(@RequestBody UserPojo userData) throws NoSuchAlgorithmException {
        if (userData == null) return ResultPojo.failError;
        if (userData.getName().isEmpty() || userData.getPassword().isEmpty())
            return ResultPojo.failError;

        return registerService.register(userData, logger);
    }
}
