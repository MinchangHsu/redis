package com.caster.redis.service.impl;

import com.caster.redis.constants.RedisConstants;
import com.caster.redis.entity.User;
import com.caster.redis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: caster
 * @date: 2020/8/31
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	/**
	 * Cacheable, CacheEvict, CachePut
	 * 這三個註解,attr key 僅能使用 spring EL 的語法去撰寫.
	 */


	@Override
	@Cacheable(value = RedisConstants.CACHE_NAME_USER, key = "T(com.caster.redis.constants.RedisConstants).USER_INFO + '_' + #root.args[0]")
	public User findUserById(Integer userId) {
		System.out.println(String.format("cache 查不到資料,結果會放入Cache,就會進入這邊,之後相同的 userId:%s 就不會再走進來.", userId));
		String name = "測試員" + StringUtils.leftPad(String.valueOf(userId), 3, "0");
		String pwd = "p" + StringUtils.leftPad(String.valueOf(userId), 3, "0");
		return new User(userId, name, pwd);
	}

	@Override
	@Cacheable(value = RedisConstants.CACHE_NAME_USER, key = "T(com.caster.redis.constants.RedisConstants).ALL_USERS")
	public List<User> findAll() {
		System.out.println("同上方 findUserById 機制.");
		List<User> list = new ArrayList<>();
		list.add(new User(51, "測試員051", "p051"));
		list.add(new User(52, "測試員052", "p052"));
		list.add(new User(53, "測試員053", "p053"));
		return list;
	}

	@Override
	@CacheEvict(value = RedisConstants.CACHE_NAME_USER, key = "T(com.caster.redis.constants.RedisConstants).USER_INFO + '_' + #root.args[0]")
	public int deleteUserById(Integer userId) {
		return 0;
	}

	@Override
	@CachePut(value = RedisConstants.CACHE_NAME_USER, key = "T(com.caster.redis.constants.RedisConstants).USER_INFO + '_' + #root.args[0].getUserId()")
	public User updateUserInfo(User userInfo) {
		System.out.println(String.format("每次都會進入,之後的 Cache 就是新的值. userInfo:%s", userInfo));
		return userInfo;
	}
}
