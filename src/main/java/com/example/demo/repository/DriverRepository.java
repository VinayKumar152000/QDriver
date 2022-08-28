package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {

	Driver findByDriverName(String driverName);
}
