package com.xxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxs.domain.Role;
import com.xxs.domain.User;
import com.xxs.domain.UserRole;
import com.xxs.mapper.UserMapper;
import com.xxs.service.IUserRoleService;
import com.xxs.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.dc.pr.PRError;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public void setRole(String userId, List<Role> roles) {

        userRoleService.remove(new QueryWrapper<>(new UserRole()).eq("user_id",userId));
        roles.forEach(role -> {
            UserRole userRole =new UserRole();
            userRole.setRoleId(role.getId());
            userRole.setUserId(userId);
            userRoleService.save(userRole);

        });
    }

    @Override
    public User getUserByUserName(String userName) {
        return getOne(new QueryWrapper<>(new User()).eq("user_name",userName));
    }
}
