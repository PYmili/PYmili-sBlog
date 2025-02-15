package icu.pymiliblog.pymillsblog.service.user;

import icu.pymiliblog.pymillsblog.common.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public interface LoginService {
    ResponseEntity<ResultPojo> login(UserPojo body) throws NoSuchAlgorithmException;
}
