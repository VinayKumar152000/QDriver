package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "driver_info")
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String driverName;
	private String gender;
	private int age;

	@OneToOne(cascade = { CascadeType.ALL })
	private Vehicle vehicle;

	@OneToOne(cascade = { CascadeType.ALL })
	private Location location;
	private boolean status = true;
	private int earning;
}
