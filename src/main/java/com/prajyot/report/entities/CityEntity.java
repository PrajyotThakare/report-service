package com.prajyot.report.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "city", schema = "world")
public class CityEntity {

	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "Name")
	private String name;

	@Column(name = "Countrycode")
	private String countryCode;
	
	@Column(name = "District")
	private String district;
	
	@Column(name = "Population")
	private String population;
}
