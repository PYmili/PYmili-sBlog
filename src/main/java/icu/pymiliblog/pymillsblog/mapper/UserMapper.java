package icu.pymiliblog.pymillsblog.mapper;

import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserPojo findUser(UserPojo user);
    Integer addUser(UserPojo user);
}
