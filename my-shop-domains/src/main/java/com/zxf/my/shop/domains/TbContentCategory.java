package com.zxf.my.shop.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zxf.my.shop.commons.persistence.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author zxf
 * @date 2018/11/7 16:06
 */
@Data
public class TbContentCategory extends BaseEntity {
    @Length(min = 1, max = 20, message = "分类名称必须介于1-20位之间")
    private String name;
    private Integer status;

    @NotNull(message = "排序不能为空")
    private Integer sortOrder;
    //打包json数据时使用的别名
    @JsonProperty(value = "isParent")
    private Boolean isParent;

    private TbContentCategory parent;
}
