package icu.pymiliblog.pymillsblog.service.blog;

import icu.pymiliblog.pymillsblog.controller.blog.FindBlogController;
import icu.pymiliblog.pymillsblog.mapper.BlogsMapper;
import icu.pymiliblog.pymillsblog.mapper.UserMapper;
import icu.pymiliblog.pymillsblog.pojo.BlogRequestPojo;
import icu.pymiliblog.pymillsblog.pojo.BlogsPojo;
import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.UserPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindBlogService {
    private static final Logger logger = LoggerFactory.getLogger(FindBlogController.class);

    @Autowired
    private BlogsMapper blogsMapper;
    @Autowired
    private UserMapper userMapper;

    public ResponseEntity<ResultPojo> findBlog(Integer id) {
        BlogsPojo blogsFindRes = blogsMapper.findBlogById(id);
        if (blogsFindRes == null) {
            logger.warn("blog id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        ResultPojo resultPojo = new ResultPojo(200, blogsFindRes);
        return ResponseEntity.ok().body(resultPojo);
    }

    public ResponseEntity<ResultPojo> findUserAllBlog(BlogRequestPojo blogRequest) {
        UserPojo findUserPojo = new UserPojo();
        findUserPojo.setId(blogRequest.getId());
        UserPojo findUserResult = userMapper.findUser(findUserPojo);
        if (findUserResult == null) {
            logger.warn("user id {} not found", blogRequest.getId());
            return ResponseEntity.notFound().build();
        }
        // 查询文章
        List<BlogsPojo> findBlogAllRes = blogsMapper.findBlogAllByRequest(blogRequest);
        if (findBlogAllRes == null) {
            logger.warn("blog id={}, author={} not found", blogRequest.getId(), blogRequest.getAuthor());
            return ResponseEntity.notFound().build();
        }
        ResultPojo resultPojo = new ResultPojo(200, findBlogAllRes);
        return ResponseEntity.ok().body(resultPojo);
    }
}
