package com.caster.redis.service;

import com.caster.redis.entity.User;

import java.util.List;

/**
 * @author: caster
 * @date: 2020/8/31
 */
public interface UserService {

	User findUserById(Integer userId);

	List<User> findAll();

	int deleteUserById(Integer userId);

	User updateUserInfo(User userInfo);
}
