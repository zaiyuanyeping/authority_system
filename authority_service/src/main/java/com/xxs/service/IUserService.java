package com.xxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxs.domain.Role;
import com.xxs.domain.User;

import java.util.List;

public interface IUserService extends IService<User> {
    void setRole(String userId, List<Role> roles);

    User getUserByUserName(String userName);
}
