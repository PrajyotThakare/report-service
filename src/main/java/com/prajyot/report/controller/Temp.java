package com.prajyot.report.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.prajyot.report.dto.CityOutDTO;

@RestController
public class Temp {

	@GetMapping("/map")
	public void main() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		List<CityOutDTO> list = restTemplate.getForObject("http://localhost:8081/cities", List.class);
		
		JSONObject jsonObj = new JSONObject();
		
		System.out.println(list);
	}
}
