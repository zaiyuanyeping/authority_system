package com.xxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxs.domain.Role;
import com.xxs.domain.RoleMenu;
import com.xxs.mapper.RoleMenuMapper;
import com.xxs.service.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
}
