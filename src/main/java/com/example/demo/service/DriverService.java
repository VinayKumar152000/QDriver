package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.bo.DriverBo;
import com.example.demo.exception.InvalidInfoException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.DriverMapper;
import com.example.demo.model.Driver;
import com.example.demo.model.Location;
import com.example.demo.model.Vehicle;
import com.example.demo.payload.DriverPayload;
import com.example.demo.payload.LocationPayload;
import com.example.demo.payload.VehiclePayload;
import com.example.demo.repository.DriverRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.VehicleRepository;

@Service
public class DriverService {

	@Autowired
	private DriverRepository driverRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private DriverMapper driverMapper;

	public DriverBo addDriver(DriverPayload payload) {

		String name = payload.getDriverName();
		String gender = payload.getGender();
		int age = payload.getAge();
		LocationPayload location = payload.getLocation();
		VehiclePayload vehiclePayload = payload.getVehicle();

		if (name == null || gender == null || age < 18 || location == null || vehiclePayload == null) {
			throw new InvalidInfoException("Driver Info Entered All Fields are Required", HttpStatus.BAD_REQUEST);
		}

		Driver driver = new Driver();
		driver.setDriverName(name);
		driver.setAge(age);
		driver.setGender(gender);

		Vehicle vehicle = new Vehicle();
		vehicle.setName(vehiclePayload.getName());
		vehicle.setNumber(vehiclePayload.getNumber());

		this.vehicleRepository.save(vehicle);

		Location loc = new Location();
		loc.setX(location.getX());
		loc.setY(location.getY());

		this.locationRepository.save(loc);

		driver.setVehicle(vehicle);
		driver.setLocation(loc);

		this.driverRepository.save(driver);

		return this.driverMapper.DrivertoDriverBo(driver);

	}

	public DriverBo updateDriverLocation(String driverName, LocationPayload location) {

		if (driverName == null || location == null) {
			throw new InvalidInfoException("DriverName or Either Location Details Required", HttpStatus.BAD_REQUEST);
		}

		Driver driver = this.driverRepository.findByDriverName(driverName);

		if (driver == null) {
			throw new ResourceNotFoundException("Driver Not Found with Given driverName", HttpStatus.NOT_FOUND);
		}

		Optional<Location> optional = this.locationRepository.findById(driver.getLocation().getId());
		Location loc = optional.get();
		loc.setX(location.getX());
		loc.setY(location.getY());

		this.locationRepository.save(loc);

		return this.driverMapper.DrivertoDriverBo(driver);

	}

	public DriverBo changeDriverStatus(String driverName, Boolean status) {

		if (driverName == null || status == null) {
			throw new InvalidInfoException("DriverName or Either Status Required", HttpStatus.BAD_REQUEST);
		}
		Driver driver = this.driverRepository.findByDriverName(driverName);

		if (driver == null) {
			throw new ResourceNotFoundException("Driver Not Found with Given driverName", HttpStatus.NOT_FOUND);
		}

		driver.setStatus(status);

		this.driverRepository.save(driver);

		return this.driverMapper.DrivertoDriverBo(driver);
	}

	public int totalEarning() {
		List<Driver> drivers = this.driverRepository.findAll();
		int total = 0;
		for (Driver driver : drivers) {
			total += driver.getEarning();
		}

		return total;
	}
}
