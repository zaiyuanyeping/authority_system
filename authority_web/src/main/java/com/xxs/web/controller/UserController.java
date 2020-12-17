package com.xxs.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.xxs.domain.Role;
import com.xxs.domain.User;
import com.xxs.result.ResponseEntity;
import com.xxs.service.IUserService;
import com.xxs.utils.ShiroUtils;
import com.xxs.utils.UUIDUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xxs.result.ResponseEntity.error;
import static com.xxs.result.ResponseEntity.success;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

//    @ResponseBody
//    @RequestMapping("/save")
//    public ResponseEntity saveaddUser(User user){
//        User user2 = userService.getUserByUserName(user.getUserName());
//        if (user2!=null){
//            return error("用户名已存在");
//        }
//        user.setId(UUIDUtils.getID());
//        user.setSalt(UUIDUtils.getID());
//        ShiroUtils.encPass(user);
//        user.setCreateTime(new Date());
//        userService.save(user);
//        return success();
//    }



    @RequestMapping("/toSetRolePage")
    public String toSetRole(@RequestParam(value = "id") String userId,HttpServletRequest request){
        request.setAttribute("userId",userId);
        return "admin/user/user_setRole";
    }

    @RequestMapping("/setRole")
    @ResponseBody
    public ResponseEntity setRole(String userId, @RequestBody List<Role> roles){
        userService.setRole(userId,roles);

        return success();
    }

    // 向用户列表页面跳转
    @RequiresPermissions("user:list")
    @RequestMapping("/tolistPage")
    public String userList(){
        return "admin/user/user_list";
    }

    /**
     * 分页查询用户列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public IPage<User> userList(int page, int limit, User user, Model model){
        // 设置分页条件
        Page<User> page2 = new Page<User>(page, limit);
        // QueryWrapper封装查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(new User());
        if (user!=null){
            if (!StringUtils.isEmpty(user.getUserName())) queryWrapper.like("user_name", user.getUserName());
            if (!StringUtils.isEmpty(user.getTel())) queryWrapper.like("tel", user.getTel());
            if (!StringUtils.isEmpty(user.getEmail())) queryWrapper.like("email", user.getEmail());
        }
        // 分页查询时，带上分页数据以及查询条件对象
        IPage<User> userIPage = userService.page(page2,queryWrapper);
        return userIPage;
    }

    @RequiresPermissions("user:add")
    @RequestMapping("/toAddPage")
    public String toAddPage(){
        return "admin/user/user_add";
    }

    @ResponseBody
    @RequestMapping("/add")
    public ResponseEntity addUser(User user){
        User user2 = userService.getUserByUserName(user.getUserName());

        if (user2!=null){
            return error("用户名已经存在");
        }
        user.setId(UUIDUtils.getID());
        user.setSalt(UUIDUtils.getID());
        ShiroUtils.encPass(user);
        user.setCreateTime(new Date());
        userService.save(user);
        return success();
    }

    @RequestMapping("/toUpdatePage")
    public String toUpdatePage(String id, HttpServletRequest request){
        User user = userService.getById(id);
        request.setAttribute("user",user);
        return "admin/user/user_update";
    }

    @ResponseBody
    @RequestMapping("/update")
    public ResponseEntity updateUser(User user){
        ShiroUtils.encPass(user);
        user.setUpdateTime(new Date());
        userService.updateById(user);
        return success();
    }

    /**
     * 删除（支持批量删除）
     */
    @ResponseBody
    @RequestMapping("/delete")
    public ResponseEntity delete(@RequestBody ArrayList<User> users){
        try {
            List<String> list = new ArrayList<String>();
            for (User user : users) {
                if ("root".equals(user.getUserName())) {
                    throw new Exception("root账号不能被删除");
                }
                list.add(user.getId());
            }
            userService.removeByIds(list);
        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        return success();
    }

}