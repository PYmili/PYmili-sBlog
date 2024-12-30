package icu.pymiliblog.pymillsblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPojo {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String secretKey;
    private String token;
}
