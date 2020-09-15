package com.xtkj.config;

import com.alibaba.fastjson.JSONObject;
import com.xtkj.pojo.User;
import com.xtkj.service.IJedisClient;
import com.xtkj.service.IUserService;
import com.xtkj.service.LoadEnum;
import com.xtkj.service.impl.JedisClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Configuration
@Slf4j
public class RoldData {
    @Autowired
    private IUserService userService;

    @Autowired
    private IJedisClient jedisClient;

    @PostConstruct
    public void load() {
        new Thread(() -> {
            log.debug("------------------启动线程准备缓存数据----------------");
            log.debug("--------------1.删除之前数据------------------------" );
            Set<String> keys = jedisClient.keys(LoadEnum.BANK.getClazz() + "*");
            Iterator<String> iterator = keys.iterator();
            while(iterator.hasNext()){
                String next = iterator.next();
                long del = jedisClient.del(next);
                log.debug("----------------->1.1删除<----------------");
            }
            log.debug("-------------------删除完毕 开始缓存-------------------");
            List<User> list = userService.list();
            for(User user:list){
                String s = JSONObject.toJSONString(user);
                jedisClient.hset(LoadEnum.BANK.getClazz(),user.getUserId().toString(),s);
            }
            log.debug("----------------------缓存完毕--------------------");
        }).start();
    }

}
