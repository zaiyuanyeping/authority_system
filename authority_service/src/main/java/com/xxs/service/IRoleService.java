package com.xxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxs.domain.Menu;
import com.xxs.domain.Role;

import java.util.List;

public interface IRoleService extends IService<Role> {
    void setMenu(String roleId, List<Menu> menus);
}
