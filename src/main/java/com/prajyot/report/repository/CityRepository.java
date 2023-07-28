package com.prajyot.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prajyot.report.entities.CityEntity;
import com.prajyot.report.view.CityView;

public interface CityRepository extends JpaRepository<CityEntity, Integer> {

	@Query("SELECT c.id as id, c.name as name, c.countryCode as countryCode, c.district as district, c.population as population from CityEntity c where c.id < 5")
	List<CityView> getCities();
}
