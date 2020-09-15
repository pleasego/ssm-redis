package com.xtkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xtkj.pojo.User;

import java.util.List;
import java.util.zip.Inflater;

public interface IUserService extends IService<User> {

    boolean delUser(Integer id);

    boolean addUser(User user);

    List<User> getList();

    boolean updUser(User user);
}
