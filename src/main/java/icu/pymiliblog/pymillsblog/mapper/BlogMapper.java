package icu.pymiliblog.pymillsblog.mapper;

import icu.pymiliblog.pymillsblog.pojo.blog.BlogPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {
    BlogPojo findBlog(Integer id);
    List<BlogPojo> findRange(Integer start, Integer number);
}
