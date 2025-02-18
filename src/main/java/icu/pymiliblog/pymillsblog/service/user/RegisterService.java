package icu.pymiliblog.pymillsblog.service.user;

import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 注册Service接口
 * @author PYmili
 */
@Component
public interface RegisterService {
    /**
     * 注册接口
     * @param body {@link UserPojo}
     * @return {@link ResponseEntity}
     */
    ResponseEntity<ApiResponseCommon> register(UserPojo body);
}
