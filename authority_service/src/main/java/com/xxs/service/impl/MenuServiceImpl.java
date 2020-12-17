package com.xxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxs.domain.Menu;
import com.xxs.mapper.MenuMapper;
import com.xxs.service.IMenuService;
import com.xxs.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
}
