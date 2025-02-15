package icu.pymiliblog.pymillsblog.service.blog;

import icu.pymiliblog.pymillsblog.mapper.BlogMapper;
import icu.pymiliblog.pymillsblog.common.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.blog.BlogPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BlogService {

    private final BlogMapper blogMapper;

    BlogService(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    public ResponseEntity<ResultPojo> findBlog(Integer id) {
        BlogPojo blog = blogMapper.findBlog(id);
        if (blog == null) {
            return ResultPojo.not_found("查找失败！");
        }
        return ResultPojo.ok(blog);
    }

    public ResponseEntity<ResultPojo> findRange(Integer start, Integer number) {
        List<BlogPojo> result = blogMapper.findRange(start, number);
        if (result.isEmpty()) {
            log.warn("blog service: find result is empty.");
            return ResultPojo.not_found("查找失败！");
        }
        return ResultPojo.ok(result);
    }
}
