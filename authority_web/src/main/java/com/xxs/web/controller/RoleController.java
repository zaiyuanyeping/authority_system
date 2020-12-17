package com.xxs.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxs.domain.Menu;
import com.xxs.domain.Role;
import com.xxs.domain.UserRole;
import com.xxs.result.PageEntity;
import com.xxs.result.ResponseEntity;
import com.xxs.service.IRoleMenuService;
import com.xxs.service.IRoleService;
import com.xxs.service.IUserRoleService;
import com.xxs.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.xxs.result.RoleEntity.error;
import static com.xxs.result.RoleEntity.success;


@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Autowired
    private IUserRoleService userRoleService;

    @ResponseBody
    @RequestMapping("/roleList")
    public PageEntity list(String userId){
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId));

        //查询所有角色
        List<Role> list = roleService.list();

//        list.forEach(role -> {
//            List<String> roleIds = userRoleList.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());
//            if (roleIds.contains(role.getId())){
//                role.setLAY_CHECKED(true);
//            }
//        });
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        list.forEach(role -> {
            // 先需要把对象转换为JSON格式
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(role));
            // 判断是否已经有了对应的权限
            List<String> roleIds = userRoleList.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());
            if(roleIds.contains(role.getId())){
                jsonObject.put("LAY_CHECKED",true);
            }
            jsonObjects.add(jsonObject);
        });

        return new PageEntity(jsonObjects.size(),jsonObjects);
    }
    /**
     *
     * @param roleId
     * @param request
     * @return
     */
    @RequestMapping("/toSetMenuPage")
    public String toSetMenuPage(@RequestParam(value = "id") String roleId, HttpServletRequest request){
        request.setAttribute("roleId",roleId);
        return "admin/role/role_setMenu";
    }

    @RequestMapping("/setMenu")
    @ResponseBody
    public ResponseEntity save(String roleId, @RequestBody List<Menu> menus){
        roleService.setMenu(roleId,menus);
//
//        RoleMenu roleMenu = new RoleMenu();
//        roleMenu.setRoleId(roleId);
//        menus.forEach(menu -> {
//            roleMenu.setMenuId(menu.getId());
//            roleMenuService.save(roleMenu);
//        });

        return success();
    }

    // 向用户列表页面跳转
    @RequestMapping("/tolistPage")
    public String roleList(){
        return "admin/role/role_list";
    }

    /**
     * 分页查询用户列表
     */
    @ResponseBody
    @RequestMapping("/list")
//    public PageEntity list(@RequestParam(value = "page",defaultValue = "1") Integer pageName,
//                           @RequestParam(value = "limit",defaultValue = "3") Integer pageSize,
//                           Role role){
//        Page<Role> page = new Page<>(pageName,pageSize);
//
//        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
//        if (role!= null && StringUtils.isNotEmpty(role.getRole())){
//            queryWrapper.like("role",role.getRole());
//        }
//
//        IPage<Role> iPage = roleService.page(page,queryWrapper);
//        return new PageEntity(iPage);
//    }
    public IPage<Role> roleList(int page, int limit, Role role, Model model){
        // 设置分页条件
        Page<Role> page2 = new Page<Role>(page, limit);
        // QueryWrapper封装查询条件
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(new Role());
        if (role!=null){
            if (!StringUtils.isEmpty(role.getRole())) queryWrapper.like("role", role.getRole());
        }
        // 分页查询时，带上分页数据以及查询条件对象
        IPage<Role> roleIPage = roleService.page(page2,queryWrapper);
        return roleIPage;
    }

    @RequestMapping("/toAddPage")
    public String toAddPage(){
        return "admin/role/role_add";
    }

    @ResponseBody
    @RequestMapping("/add")
    public ResponseEntity addRole(Role role){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",role.getRole());
        Role one = roleService.getOne(queryWrapper);

        if (one!=null){
            return error("用户名已经存在");
        }
        role.setId(UUIDUtils.getPwd());
        role.setCreateTime(new Date());

        roleService.save(role);
        return success();
    }

    @RequestMapping("/updatePage")
    public String toUpdatePage(String id, Model model){
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
        return "admin/role/role_update";
    }

    @ResponseBody
    @RequestMapping("/update")
    public ResponseEntity updateRole(Role role){
        role.setUpdateTime(new Date());
        roleService.updateById(role);
        return success();
    }

    /**
     * 删除（支持批量删除）
     */
    @ResponseBody
    @RequestMapping("/delete")
    public ResponseEntity delete(@RequestBody ArrayList<Role> roles){
        try {
            List<String> list = new ArrayList<String>();
            for (Role role : roles) {
                if ("root".equals(role.getRole())) {
                    throw new Exception("root账号不能被删除");
                }
                list.add(role.getId());
            }
            roleService.removeByIds(list);
        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        return success();
    }

}