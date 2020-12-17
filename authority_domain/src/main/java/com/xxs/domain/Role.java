package com.xxs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@TableName("t_role")
@Data

public class Role extends BaseEntity{

    private String role; // 角色名称
    private String remark; // 备注

//    @TableField(exist = false)
//    private boolean LAY_CHECKED=false;
}
