package icu.pymiliblog.pymillsblog.service.user.impl;

import icu.pymiliblog.pymillsblog.mapper.UserMapper;
import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import icu.pymiliblog.pymillsblog.service.user.LoginService;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import icu.pymiliblog.pymillsblog.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * LoginService的实现
 * @author PYmili
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    // 用户操作的Mapper
    private final UserMapper userMapper;

    public LoginServiceImpl(UserMapper mapper) {
        this.userMapper = mapper;
    }

    /**
     * 登录接口实现
     * @param requestBody {@link UserPojo}
     * @return {@link ResponseEntity}
     * @throws NoSuchAlgorithmException
     */
    @Override
    public ResponseEntity<ApiResponseCommon> login(UserPojo requestBody) throws NoSuchAlgorithmException {
        // 查询用户
        UserPojo findUserPojo = new UserPojo();
        findUserPojo.setName(requestBody.getName());
        UserPojo findResult = userMapper.findByPojo(findUserPojo);
        if (findResult == null) {
            // 未查询到则返回错误
            log.warn("Login service: not user \"{}\"", requestBody.getName());
            return ApiResponseCommon.not_found("用户或密码错误！");
        }
        log.info("Login service: find result: {}", findResult);

        // 处理密码
        String salt = findResult.getSalt();
        String requestPwdHash = requestBody.getPasswordHash();
        String passwordHeaded = PasswordUtils.hashPassword(requestPwdHash, salt);
        if (!findResult.getPasswordHash().equals(passwordHeaded)) {
            log.warn("Login service: Password error.");
            return ApiResponseCommon.not_found("用户或密码错误！");
        }

        // 生成 jwt token
        Map<String, Object> map = new HashMap<>();
        map.put("id", requestBody.getId());
        map.put("name", requestBody.getName());
        String jwt = JwtUtils.createJwt(1, map, "login");

        return ResponseEntity.ok().body(new ApiResponseCommon(200,  jwt));
    }

}
