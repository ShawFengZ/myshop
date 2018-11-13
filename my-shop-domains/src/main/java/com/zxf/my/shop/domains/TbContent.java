package com.zxf.my.shop.domains;

import com.zxf.my.shop.commons.persistence.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 内容管理
 * @author zxf
 * @date 2018/11/8 9:30
 */
@Data
public class TbContent extends BaseEntity {

    @Length(min = 2, max = 20, message = "标题的长度必须介于1-20之间")
    private String title;
    @Length(min = 2, max = 20, message = "标题的长度必须介于1-20之间")
    private String subTitle;
    @NotBlank(message = "标题描述不能为空")
    private String titleDesc;
    private String url;
    private String pic;
    private String pic2;
    @Length(min = 1, message = "内容不能为空")
    private String content;
    @NotNull(message = "父级类目不能为空")
    private TbContentCategory tbContentCategory;
}
