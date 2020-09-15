package com.xtkj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtkj.dao.UserMapper;
import com.xtkj.pojo.User;
import com.xtkj.service.IJedisClient;
import com.xtkj.service.IUserService;
import com.xtkj.service.LoadEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IJedisClient jedisClient;

    @Override
    public boolean delUser(Integer id) {//删除用户
        User byId = userService.getById(id);
        boolean b = userService.removeById(id);
        if(b){
            jedisClient.hdel(LoadEnum.BANK.getClazz(), byId.getUserId().toString());
            System.out.println("缓存成功");
        }
        return b;
    }

    @Override
    public boolean addUser(User user) {//添加用户
        boolean save = userService.save(user);
        if(save && user.getUserId()!=null){
            String s = JSONObject.toJSONString(user);
            jedisClient.hset(LoadEnum.BANK.getClazz(), user.getUserId().toString(),s);
            System.out.println("添加缓存成功");
        }
        return save;
    }

    @Override
    public List<User> getList() {//查询所有用户
        List<User> users = jedisClient.hgetAll(LoadEnum.BANK.getClazz());
        if(users.isEmpty()){
             users = userService.list();
        }
        return users;
    }

    @Override
    public boolean updUser(User user) {//修改用户
        User user1 = userService.getById(user.getUserId());
        user.setVersion(user1.getVersion()+1);
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("version",user1.getVersion())
               .eq ("user_id",user1.getUserId());
        boolean update = userService.update(user, wrapper);

        if(update){
            String s = JSONObject.toJSONString(user1);
            jedisClient.hset(LoadEnum.BANK.getClazz(), user1.getUserId().toString(),s);
            System.out.println("修改缓存成功");
        }
        return update;
    }
}
