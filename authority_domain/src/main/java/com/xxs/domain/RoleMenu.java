package com.xxs.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色菜单表
 */
@TableName("t_role_menu")
@Data

public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private String menuId;  // 菜单id
    private String roleId; // 角色id
    // get、set方法，toString方法
}
