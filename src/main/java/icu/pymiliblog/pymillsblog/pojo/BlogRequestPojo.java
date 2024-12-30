package icu.pymiliblog.pymillsblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequestPojo {
    private Integer id;
    private String author;
    private String jwt;
}
