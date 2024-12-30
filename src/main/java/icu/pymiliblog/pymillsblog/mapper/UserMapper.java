package icu.pymiliblog.pymillsblog.mapper;

import icu.pymiliblog.pymillsblog.pojo.UserPojo;
import icu.pymiliblog.pymillsblog.pojo.UserRequestPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public UserPojo findUser(UserPojo user);
    public int addUser(UserPojo user);
    public int updateUser(
            @Param("requestPojo") UserRequestPojo requestPojo,
            @Param("user") UserPojo user
    );
}
