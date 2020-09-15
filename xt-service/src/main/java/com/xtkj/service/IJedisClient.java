package com.xtkj.service;

import com.xtkj.pojo.User;

import java.util.List;
import java.util.Set;

public interface IJedisClient {

    String get(String key);

    String set(String key, String value);

    long ttl(String key);

    long expire(String key, int second);

    long incr(String key);

    long hset(String hkey, String key, String value);

    String hget(String hkey, String key);

    long del(String key);

    long hdel(String hkey, String key);

    Set<String> keys(String k);

    List<User> hgetAll(String k);
}
