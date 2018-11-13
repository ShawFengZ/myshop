package com.zxf.my.shop.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zxf.my.shop.commons.persistence.BaseEntity;
import com.zxf.my.shop.commons.utils.RegexpUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * @author zxf
 * @date 2018/10/26 14:38
 */
@Data
public class User extends BaseEntity {

    @Length(min = 3, max = 6, message = "用户名长度必须介于3和6之间")
    private String username;

    @Email
    private String email;

    @JsonIgnore
    private String password;


    @Pattern(regexp = RegexpUtils.PHONE, message = "手机格式不符合，请重新输入")
    private String phone;

}
