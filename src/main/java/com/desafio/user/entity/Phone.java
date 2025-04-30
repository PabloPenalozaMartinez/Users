package com.desafio.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Phone")
public class Phone {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "number")
	private Long number;

	@Column(name = "city_code")
	private Long citycode;

	@Column(name = "country_code")
	private Long countrycode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getCitycode() {
		return citycode;
	}

	public void setCitycode(Long citycode) {
		this.citycode = citycode;
	}

	public Long getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(Long countrycode) {
		this.countrycode = countrycode;
	}

}
