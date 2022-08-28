package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.bo.UserBo;
import com.example.demo.exception.InvalidInfoException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Location;
import com.example.demo.model.User;
import com.example.demo.payload.LocationPayload;
import com.example.demo.payload.UserPayload;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserMapper userMapper;

	public UserBo addUser(UserPayload payload) {

		String name = payload.getUserName();
		String gender = payload.getGender();
		int age = payload.getAge();
		LocationPayload location = payload.getLocation();

		if (name == null || gender == null || age < 18 || location == null) {
			throw new InvalidInfoException("Driver Info Entered All Fields are Required", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setUserName(name);
		user.setGender(gender);
		user.setAge(age);

		Location loc = new Location();
		loc.setX(location.getX());
		loc.setY(location.getY());

		this.locationRepository.save(loc);
		user.setLocation(loc);

		this.userRepository.save(user);

		return this.userMapper.UsertoUserBo(user);
	}

	public UserBo updateUser(String userName, UserPayload payload) {

		String name = payload.getUserName();
		String gender = payload.getGender();
		int age = payload.getAge();
		LocationPayload location = payload.getLocation();

		if (name == null || gender == null || age < 18 || location == null) {
			throw new InvalidInfoException("Driver Info Entered All Fields are Required", HttpStatus.BAD_REQUEST);
		}

		User user = this.userRepository.findByUserName(userName);

		if (user == null) {
			throw new ResourceNotFoundException("User Not Found with Given userName", HttpStatus.NOT_FOUND);
		}
		user.setUserName(name);
		user.setGender(gender);
		user.setAge(age);

		Optional<Location> optional = this.locationRepository.findById(user.getLocation().getId());
		Location loc = optional.get();
		loc.setX(location.getX());
		loc.setY(location.getY());
		this.locationRepository.save(loc);
		this.userRepository.save(user);

		return this.userMapper.UsertoUserBo(user);

	}

	public UserBo updateUserLocation(String userName, LocationPayload location) {

		User user = this.userRepository.findByUserName(userName);

		if (user == null) {
			throw new ResourceNotFoundException("User Not Found with Given userName", HttpStatus.NOT_FOUND);
		}
		
		Optional<Location> optional = this.locationRepository.findById(user.getLocation().getId());
		Location loc = optional.get();
		loc.setX(location.getX());
		loc.setY(location.getY());

		this.locationRepository.save(loc);

		return this.userMapper.UsertoUserBo(user);

	}
}
