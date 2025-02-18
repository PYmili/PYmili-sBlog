package icu.pymiliblog.pymillsblog.service.user.impl;

import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import icu.pymiliblog.pymillsblog.service.user.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 对InfoService的实现
 * @author PYmili
 */
@Slf4j
@Service
public class InfoServiceImpl implements InfoService {

    @Override
    public ResponseEntity<ApiResponseCommon> userInfo(UserPojo userPojo) {
        return null;
    }

}
