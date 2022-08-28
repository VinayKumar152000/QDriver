package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bo.DriverBo;
import com.example.demo.payload.RidePayload;
import com.example.demo.service.RideService;

@RestController
@RequestMapping("api/rides")
public class RideController {

	@Autowired
	private RideService rideService;

	@GetMapping
	public List<DriverBo> findRide(@RequestBody RidePayload rideinfo) {
		return rideService.findRide(rideinfo);
	}

	@GetMapping("drive/{userName}")
	public DriverBo chooseRide(@PathVariable(value = "userName") String userName, @RequestBody String driverName) {
		return rideService.chooseRide(userName, driverName);
	}

	@GetMapping("/{userName}")
	public int calculateBill(@PathVariable(value = "userName") String userName) {
		return rideService.calculateBill(userName);
	}

}
