package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.bo.DriverBo;
import com.example.demo.bo.UserBo;
import com.example.demo.exception.InvalidInfoException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.DriverMapper;
import com.example.demo.model.Driver;
import com.example.demo.model.Location;
import com.example.demo.model.User;
import com.example.demo.payload.LocationPayload;
import com.example.demo.payload.RidePayload;
import com.example.demo.repository.DriverRepository;
import com.example.demo.repository.UserRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class RideService {

	@Autowired
	private DriverRepository driverRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DriverMapper driverMapper;

	@Autowired
	private RestTemplate restTemplate;

	public List<DriverBo> findRide(RidePayload ride) {

		List<DriverBo> ans = new ArrayList<>();
		List<Driver> drivers = this.driverRepository.findAll();
		String userName = ride.getUserName();
		LocationPayload pickupLocation = ride.getPickupLocation();
		LocationPayload dropLocation = ride.getDropLocation();

		if (userName == null || pickupLocation == null || dropLocation == null) {
			throw new InvalidInfoException("Ride Info Required", HttpStatus.BAD_REQUEST);
		}

		for (Driver driver : drivers) {
			Location loc = driver.getLocation();
			int distance = (int) Math.sqrt(
					Math.pow(loc.getX() - pickupLocation.getX(), 2) + Math.pow(loc.getY() - pickupLocation.getY(), 2));
			if (distance > 0 && distance <= 5 && driver.isStatus() == true) {
				DriverBo driverbo= this.driverMapper.DrivertoDriverBo(driver);
				ans.add(driverbo);
			}
		}

		int distance = (int) Math.sqrt(Math.pow(dropLocation.getX() - pickupLocation.getX(), 2)
				+ Math.pow(dropLocation.getY() - pickupLocation.getY(), 2));
		User user = this.userRepository.findByUserName(userName);
		if (user == null) {
			throw new ResourceNotFoundException("User Not Found with Given userName", HttpStatus.NOT_FOUND);
		}
		user.setDistance(distance);
		user.setDropx(dropLocation.getX());
		user.setDropy(dropLocation.getY());

		this.userRepository.save(user);
		return ans;
	}

	public DriverBo chooseRide(String userName, String driverName) {

		if (userName == null || driverName == null) {
			throw new InvalidInfoException("UserName and DriverName Info Required", HttpStatus.BAD_REQUEST);
		}

		User user = this.userRepository.findByUserName(userName);
		if (user == null) {
			throw new ResourceNotFoundException("User Not Found with Given userName", HttpStatus.NOT_FOUND);
		}

		Driver driver = this.driverRepository.findByDriverName(driverName.substring(1, driverName.length() - 1));
		if (driver == null) {
			throw new ResourceNotFoundException("Driver Not Found with Given driverName", HttpStatus.NOT_FOUND);
		}

		user.setDriver(driver);
		driver.setStatus(false);
		this.userRepository.save(user);
		this.driverRepository.save(driver);

		return this.driverMapper.DrivertoDriverBo(driver);
	}

	public int calculateBill(String userName) {

		if (userName == null) {
			throw new InvalidInfoException("UserName Info Required", HttpStatus.BAD_REQUEST);
		}

		User user = this.userRepository.findByUserName(userName);

		if (user == null) {
			throw new ResourceNotFoundException("User Not Found with Given userName", HttpStatus.NOT_FOUND);
		}

		Optional<Driver> optional = this.driverRepository.findById(user.getDriver().getId());

		Driver driver = optional.get();

		int price = (int) user.getDistance() * 10;
		int total = driver.getEarning() + price;
		driver.setEarning(total);
		driver.setStatus(true);

		String driverName = driver.getDriverName();

		user.setDriver(null);
		this.userRepository.save(user);
		this.driverRepository.save(driver);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		URI uri1 = null;
		URI uri2 = null;
		try {
			uri1 = new URI("http://localhost:8081/api/users");
			uri2 = new URI("http://localhost:8081/api/driver");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		LocationPayload location = new LocationPayload();
		location.setX(user.getDropx());
		location.setY(user.getDropy());
		HttpEntity<LocationPayload> httpEntity = new HttpEntity<>(location, headers);

		restTemplate.exchange(uri1 + "/" + userName, HttpMethod.PATCH, httpEntity, UserBo.class);
		restTemplate.exchange(uri2 + "/" + driverName, HttpMethod.PATCH, httpEntity, DriverBo.class);

		return price;

	}
}
