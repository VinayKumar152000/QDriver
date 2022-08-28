package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RidePayload {

	private String userName;
	private LocationPayload pickupLocation;
	private LocationPayload dropLocation;
}
