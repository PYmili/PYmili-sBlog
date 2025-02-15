package icu.pymiliblog.pymillsblog.service.user;

import icu.pymiliblog.pymillsblog.common.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface InfoService {
    ResponseEntity<ResultPojo> userInfo(UserPojo pojo);
}
