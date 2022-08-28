package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverPayload {

	private String driverName;
	private String gender;
	private int age;
	private VehiclePayload vehicle;
	private LocationPayload location;
}
