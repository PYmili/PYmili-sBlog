package icu.pymiliblog.pymillsblog.service.user.impl;

import icu.pymiliblog.pymillsblog.mapper.UserMapper;
import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import icu.pymiliblog.pymillsblog.service.user.RegisterService;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import icu.pymiliblog.pymillsblog.utils.PasswordUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * RegisterService 注册接口的实现
 * @author PYmili
 */
@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {

    // 用户操作的Mapper
    private final UserMapper userMapper;

    public RegisterServiceImpl(UserMapper mapper) {
        this.userMapper = mapper;
    }

    /**
     * 验证用户是否存在
     * @param username {@link String}
     * @return boolean
     */
    private boolean existUser(String username) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(username);
        UserPojo findResult = userMapper.findByPojo(userPojo);
        return findResult != null;
    }

    /**
     * 添加用户的Service
     * @param userPojo {@link UserPojo}
     * @return {@link Integer} 用户id
     */
    private Integer addUser(UserPojo userPojo) {
        return userMapper.addUser(userPojo);
    }

    /**
     * 注册实现
     * @param requestBody {@link UserPojo}
     * @return {@link ResponseEntity}
     */
    @SneakyThrows
    @Override
    public ResponseEntity<ApiResponseCommon> register(UserPojo requestBody) {
        // 查看用户是否重复
        if (existUser(requestBody.getName())) {
            log.warn("user register service: 用户 {} 已经存在！", requestBody.getName());
            return ApiResponseCommon.not_found("用户存在！");
        }

        // 注册用户
        String salt = JwtUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(requestBody.getPasswordHash(), salt);
        requestBody.setSalt(salt);
        requestBody.setPasswordHash(hashedPassword);
        Integer id = addUser(requestBody);
        if (id == null || id == 0) {
            return new ResponseEntity<>(
                    new ApiResponseCommon(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "注册失败！"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 生成jwt
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("user", requestBody.getName());
        String jwt = JwtUtils.createJwt(1, map, "register");

        return ApiResponseCommon.ok(jwt);
    }

}
