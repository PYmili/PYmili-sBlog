package icu.pymiliblog.pymillsblog.mapper;

import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户的Mapper
 * @author PYmili
 */
@Mapper
public interface UserMapper {
    /**
     * 通过{@link UserPojo}查找
     * @param user {@link UserPojo}
     * @return {@link UserPojo}
     */
    UserPojo findByPojo(UserPojo user);

    /**
     * 添加用户
     * @param user {@link UserPojo}
     * @return {@link Integer}
     */
    Integer addUser(UserPojo user);
}
