package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bo.UserBo;
import com.example.demo.payload.LocationPayload;
import com.example.demo.payload.UserPayload;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping
	public UserBo addUser(@RequestBody UserPayload user) {
		return userService.addUser(user);
	}
	
	@PutMapping("/{userName}")
	public UserBo updateUser(@PathVariable(value="userName") String userName,@RequestBody UserPayload user) {
		return userService.updateUser(userName,user);
	}
	
	@PatchMapping("/{userName}")
	public UserBo updateUserLocation(@PathVariable(value="userName") String userName,@RequestBody LocationPayload location) {
		return userService.updateUserLocation(userName,location);
	}
}
