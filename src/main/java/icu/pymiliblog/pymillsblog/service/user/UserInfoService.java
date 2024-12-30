package icu.pymiliblog.pymillsblog.service.user;

import icu.pymiliblog.pymillsblog.mapper.UserMapper;
import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.UserPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
    @Autowired
    private UserMapper mapper;

    public ResponseEntity<ResultPojo> userInfo(UserPojo userPojo) {
        UserPojo result = mapper.findUser(userPojo);
        if (result == null) {
            logger.warn("User {} not found", userPojo.getName());
            return ResultPojo.not_found("user " + userPojo.getName() + " not found!");
        }
        result.setToken(null);
        result.setPassword(null);
        result.setSecretKey(null);
        return ResponseEntity.ok().body(new ResultPojo(200, result));
    }
}
