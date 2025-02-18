package icu.pymiliblog.pymillsblog.mapper;

import icu.pymiliblog.pymillsblog.pojo.blog.BlogPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 博客的Mapper
 * @author PYmili
 */
@Mapper
public interface BlogMapper {
    /**
     * 通过id查找博客数据
     * @param id {@link Integer}
     * @return {@link BlogPojo}
     */
    BlogPojo findById(Integer id);

    /**
     * 通过start和number查找
     * @param start {@link Integer} 初始位置
     * @param number {@link Integer} 需要获取的数量
     * @return {@link List}
     */
    List<BlogPojo> findByRange(Integer start, Integer number);
}
