package com.prajyot.report.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.prajyot.report.config.YamlConfig;
import com.prajyot.report.dto.CityOutDTO;
import com.prajyot.report.repository.CityRepository;
import com.prajyot.report.service.ReportService;
import com.prajyot.report.view.CityView;

import net.sf.jasperreports.engine.JRException;


@RestController
public class MovieController {

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ReportService reportService;
	
	@Value("${ReportLocation}")
	private String path;
	
	@Autowired
	private YamlConfig yamlConfig;
	
	@GetMapping("/home")
	public List<Integer> getHomePage(){
		return List.of(1,2,3,4,5,6);
	}
	
	@GetMapping("/yaml")
	public void getYamlDetails(){
		System.out.println(path);
		System.out.println(yamlConfig.getReportMap());
	}
	
	@GetMapping("/cities")
	public List<CityOutDTO> getCities(){
		
		List<CityView> viewList = cityRepository.getCities();
		
		List<CityOutDTO> response = new ArrayList<>();
		
		for(CityView view : viewList) {
		
			CityOutDTO outDTO = new CityOutDTO();
			outDTO.setCountryCode(view.getCountryCode());
			outDTO.setDistrict(view.getDistrict());
			outDTO.setId(view.getId());
			outDTO.setName(view.getName());
			outDTO.setPopulation(view.getPopulation());
			response.add(outDTO);
		}
		 
		List<Integer> ids = response.stream().map(p -> Integer.parseInt(p.getId())).filter(i -> i%2 == 0).collect(Collectors.toList());
		System.out.println(ids);
		return response;
	}
	
	@GetMapping("/cityById/{id}")
	public CityOutDTO getCityById(@PathVariable Integer id){
		
		CityView view = (CityView) cityRepository.getById(id);
		return null;
		 
	}
	
	@GetMapping("/get-report/{reportFormat}")
	public String getReport(@PathVariable String reportFormat) throws FileNotFoundException, JRException {

		List<CityView> viewList = cityRepository.getCities();

		List<CityOutDTO> cities = new ArrayList<>();

		for (CityView view : viewList) {

			CityOutDTO outDTO = new CityOutDTO();
			outDTO.setCountryCode(view.getCountryCode());
			outDTO.setDistrict(view.getDistrict());
			outDTO.setId(view.getId());
			outDTO.setName(view.getName());
			outDTO.setPopulation(view.getPopulation());
			cities.add(outDTO);
		}
		
		reportService.generateReport(reportFormat, cities);
		
		return "Report Generated Successfully";
	}
}
