package icu.pymiliblog.pymillsblog.mapper;

import icu.pymiliblog.pymillsblog.pojo.BlogRequestPojo;
import icu.pymiliblog.pymillsblog.pojo.BlogsPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogsMapper {
    public BlogsPojo findBlogById(int id);
    public List<BlogsPojo> findBlogAllByRequest(@Param("request") BlogRequestPojo blogRequestPojo);
}
