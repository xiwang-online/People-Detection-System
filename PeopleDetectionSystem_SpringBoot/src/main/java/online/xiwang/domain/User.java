package online.xiwang.domain;

import lombok.Data;


@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String usertype;
    private String avatar;
    private String remark;
    private String phone;

}
