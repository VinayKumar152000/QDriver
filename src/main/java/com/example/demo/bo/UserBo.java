package com.example.demo.bo;

import com.example.demo.model.Location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBo {

	private Long userId;
	private String userName;
	private String gender;
	private int age;
	private Location location;
}
