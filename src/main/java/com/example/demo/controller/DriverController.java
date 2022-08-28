package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bo.DriverBo;
import com.example.demo.payload.DriverPayload;
import com.example.demo.payload.LocationPayload;
import com.example.demo.service.DriverService;

@RestController
@RequestMapping("api/driver")
public class DriverController {

	@Autowired
	private DriverService driverService;

	@PostMapping
	public DriverBo addDriver(@RequestBody DriverPayload driver) {
		return driverService.addDriver(driver);
	}

	@PatchMapping("/{driverName}")
	public DriverBo updateDriverLocation(@PathVariable(value = "driverName") String driverName,
			@RequestBody LocationPayload payload) {
		return driverService.updateDriverLocation(driverName, payload);
	}

	@PatchMapping("/location/{driverName}")
	public DriverBo changeDriverStatus(@PathVariable(value = "driverName") String driverName,
			@RequestBody Boolean status) {
		return driverService.changeDriverStatus(driverName, status);
	}
	
	@GetMapping("/earnings")
	public int totalEarning() {
		return driverService.totalEarning();
	}
}
