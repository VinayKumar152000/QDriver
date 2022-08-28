package com.example.demo.bo;

import com.example.demo.model.Location;
import com.example.demo.model.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverBo {

	private Long id;
	private String driverName;
	private String gender;
	private int age;
	private Vehicle vehicle;
	private Location location;
	private boolean status = true;
}
