package com.xxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxs.domain.Menu;
import com.xxs.domain.UserRole;
import com.xxs.mapper.MenuMapper;
import com.xxs.mapper.UserRoleMapper;
import com.xxs.service.IMenuService;
import com.xxs.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
