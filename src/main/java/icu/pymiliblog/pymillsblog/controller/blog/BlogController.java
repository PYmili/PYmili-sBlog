package icu.pymiliblog.pymillsblog.controller.blog;

import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.service.blog.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/blog")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问)
public class BlogController {

    private final BlogService blogService;

    BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/find")
    public ResponseEntity<ResultPojo> findBlog(@RequestParam(value = "id") Integer id) {
        if (id == null || id <= 0) {
            log.warn("/blog/find: request params error.");
            return ResultPojo.not_found("参数错误！");
        }
        log.info("/blog/find: request params: id = {}", id);
        return blogService.findBlog(id);
    }

    @RequestMapping(value = "/find-range", method = RequestMethod.POST)
    public ResponseEntity<ResultPojo> findBlogAll(
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "number", required = false) Integer number) {
        if (start == null || start < 0 || number == null || number <= 0) {
            return ResultPojo.not_found("参数错误！");
        }
        log.info("/blog/find-range: start: {}, number: {}", start, number);
        return blogService.findRange(start, number);
    }
}
