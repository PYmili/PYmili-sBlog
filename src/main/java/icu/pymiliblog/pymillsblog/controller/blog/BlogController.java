package icu.pymiliblog.pymillsblog.controller.blog;

import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.service.blog.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 博客 Controller
 * @author PYmili
 */
@Slf4j
@RestController
@RequestMapping("/blog")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问)
public class BlogController {

    // 博客的Service
    private final BlogService blogService;

    BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * 通过id查找
     * @param id {@link Integer}
     * @return {@link ResponseEntity}
     */
    @GetMapping("/find")
    public ResponseEntity<ApiResponseCommon> findById(@RequestParam(value = "id") Integer id) {
        if (id == null || id <= 0) {
            log.warn("/blog/find: request params error.");
            return ApiResponseCommon.not_found("参数错误！");
        }
        log.info("/blog/find: request params: id = {}", id);
        return blogService.findById(id);
    }

    /**
     * 通过start和number查找
     * @param start {@link Integer} 起始位置
     * @param number {@link Integer} 需要查找的数量
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "/find-range", method = RequestMethod.POST)
    public ResponseEntity<ApiResponseCommon> findByRange(
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "number", required = false) Integer number) {
        if (start == null || start < 0 || number == null || number <= 0) {
            return ApiResponseCommon.not_found("参数错误！");
        }
        log.info("/blog/find-range: start: {}, number: {}", start, number);
        return blogService.findByRange(start, number);
    }
}
