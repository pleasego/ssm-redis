package com.xtkj.controller;

import com.xtkj.pojo.User;
import com.xtkj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/addUser")
    public boolean addUser(User user){//添加用户
       return userService.addUser(user);
    }


    @GetMapping("/delUser")
    public boolean delUser(Integer id){//删除用户
        return userService.delUser(id);
    }

    @GetMapping("/getList")
    public List<User> getList() {//查询所有用户
        return userService.getList();
    }

    @PostMapping("/updUser")
    public boolean updUser(User user){//修改用户
        return userService.updUser(user);
    }
}
