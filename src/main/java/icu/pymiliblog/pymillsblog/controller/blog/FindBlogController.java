package icu.pymiliblog.pymillsblog.controller.blog;

import icu.pymiliblog.pymillsblog.pojo.BlogRequestPojo;
import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.UserPojo;
import icu.pymiliblog.pymillsblog.service.blog.FindBlogService;
import icu.pymiliblog.pymillsblog.service.user.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/blogs")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 允许所有域跨域访问)
public class FindBlogController {
    private static final Logger logger = LoggerFactory.getLogger(FindBlogController.class);

    @Autowired
    private FindBlogService findBlogService;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/find")
    public ResponseEntity<ResultPojo> findBlog(@RequestBody BlogRequestPojo findReq) {
        if (findReq == null ||
                findReq.getId() == 0 ||
                findReq.getAuthor() == null ||
                findReq.getJwt() == null
        ) {
            logger.warn("/find: 请求参数为空或参数残缺");
            return ResultPojo.not_found("请求参数为空或参数残缺");
        }
        if (JwtInspection(findReq)) return ResultPojo.not_found("jwt error!");
        return findBlogService.findBlog(findReq.getId());
    }

    @RequestMapping("/find_all")
    public ResponseEntity<ResultPojo> findBlogAll(@RequestBody BlogRequestPojo blogRequest) {
        if (blogRequest == null) {
            logger.warn("blogRequest is null or empty.");
            return ResultPojo.not_found("request param is null.");
        }
        if (JwtInspection(blogRequest)) return ResultPojo.not_found("jwt error!");
        return findBlogService.findUserAllBlog(blogRequest);
    }

    private boolean JwtInspection(BlogRequestPojo findReq) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(findReq.getAuthor());
        userPojo.setToken(findReq.getJwt());
        ResponseEntity<ResultPojo> resultPojoResponseEntity = userInfoService.userInfo(userPojo);
        return resultPojoResponseEntity.getStatusCode() != HttpStatus.OK;
    }
}
