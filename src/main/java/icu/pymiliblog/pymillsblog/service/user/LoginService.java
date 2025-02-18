package icu.pymiliblog.pymillsblog.service.user;

import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

/**
 * 用户登录Service接口
 * @author PYmili
 */
@Component
public interface LoginService {
    /**
     * 登录接口Service
     * @param body {@link UserPojo}
     * @return {@link ResponseEntity}
     * @throws NoSuchAlgorithmException
     */
    ResponseEntity<ApiResponseCommon> login(UserPojo body) throws NoSuchAlgorithmException;
}
