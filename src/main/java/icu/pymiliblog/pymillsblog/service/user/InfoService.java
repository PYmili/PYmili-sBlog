package icu.pymiliblog.pymillsblog.service.user;

import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 用户信息Service接口
 * @author PYmili
 */
@Component
public interface InfoService {
    /**
     * 用户信息
     * @param pojo {@link UserPojo}
     * @return {@link ResponseEntity}
     */
    ResponseEntity<ApiResponseCommon> userInfo(UserPojo pojo);
}
