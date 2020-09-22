package com.caster.redis.controller;

import com.caster.redis.controller.request.InsertUserReq;
import com.caster.redis.entity.User;
import com.caster.redis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author: caster
 * @date: 2020/8/31
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements Serializable {

	final UserService userService;

	// http://localhost:8080/user/1
	@GetMapping("/{userId}")
	public User findUserById(@PathVariable String userId) {
		return userService.findUserById(Integer.parseInt(userId));
	}

	// http://localhost:8080/user/findAll
	@GetMapping("/findAll")
	public List<User> findAll() {
		return userService.findAll();
	}

	// http://localhost:8080/user/1
	@DeleteMapping("/{userId}")
	public int deleteUserById(@PathVariable String userId) {
		return userService.deleteUserById(Integer.parseInt(userId));
	}

	// http://localhost:8080/user/1
	@PutMapping("/{userId}")
	public User updateUserInfo(@PathVariable String userId,
							   @RequestParam String name,
							   @RequestParam String pwd) {
		return userService.updateUserInfo(new User(Integer.parseInt(userId), name, pwd));
	}

	@PostMapping("")
	public User findUserById(@RequestBody InsertUserReq req) {
		User user = new User();
		BeanUtils.copyProperties(req, user);
		return userService.insertUser(user);
	}
}
