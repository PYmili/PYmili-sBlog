package icu.pymiliblog.pymillsblog.service.blog;

import icu.pymiliblog.pymillsblog.mapper.BlogMapper;
import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.pojo.blog.BlogPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博客的Service
 * @author PYmili
 */
@Slf4j
@Service
public class BlogService {

    private final BlogMapper blogMapper;

    BlogService(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    /**
     * 通过id查找
     * @param id {@link Integer}
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponseCommon> findById(Integer id) {
        BlogPojo blog = blogMapper.findById(id);
        if (blog == null) {
            return ApiResponseCommon.not_found("查找失败！");
        }
        return ApiResponseCommon.ok(blog);
    }

    /**
     * 通过start和number查找
     * @param start {@link Integer} 起始位置
     * @param number {@link Integer} 需要获取的数量
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponseCommon> findByRange(Integer start, Integer number) {
        List<BlogPojo> result = blogMapper.findByRange(start, number);
        if (result.isEmpty()) {
            log.warn("blog service: find result is empty.");
            return ApiResponseCommon.not_found("查找失败！");
        }
        return ApiResponseCommon.ok(result);
    }
}
