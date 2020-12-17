package com.xxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxs.domain.Menu;
import com.xxs.domain.Role;
import com.xxs.domain.RoleMenu;
import com.xxs.mapper.RoleMapper;
import com.xxs.service.IRoleMenuService;
import com.xxs.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    public IRoleMenuService roleMenuService;

    @Override
    public void setMenu(String roleId, List<Menu> menus) {
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",roleId));

        menus.forEach(menu -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menu.getId());
            roleMenuService.save(roleMenu);
        });
    }
}
