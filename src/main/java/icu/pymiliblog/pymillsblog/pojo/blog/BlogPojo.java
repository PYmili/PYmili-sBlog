package icu.pymiliblog.pymillsblog.pojo.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogPojo {
    private Integer id;
    private String title;
    private byte[] topImg;
    private String introduction;
    private String content;
    private Integer authorID;
    private LocalDateTime createDate;
    private Boolean published;
}
