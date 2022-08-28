package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bo.DriverBo;
import com.example.demo.model.Driver;

@Service
public class DriverMapper {

	@Autowired
	private ModelMapper modelMapper;

	public DriverBo DrivertoDriverBo(Driver user) {
		DriverBo driverbo = this.modelMapper.map(user, DriverBo.class);
		return driverbo;
	}
}
