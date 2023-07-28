package com.prajyot.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityOutDTO {

	private String id;
	private String name;
	private String countryCode;
	private String district;
	private String population;		
}
