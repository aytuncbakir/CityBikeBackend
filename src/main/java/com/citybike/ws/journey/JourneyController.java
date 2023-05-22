package com.citybike.ws.journey;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.citybike.ws.shared.GenericResponse;

@RestController
public class JourneyController {
	
	@Autowired
	JourneyService journeyService;
	
	@GetMapping("/api/1.0/journeys/departure/count")
	@ResponseBody
	ResponseEntity<Long> getStationAsDeparture(@RequestParam String id){
			long count = journeyService.getStationAsDeparture(id);
		
		return ResponseEntity.ok().body(count);
	}
	
	@GetMapping("/api/1.0/journeys/return/count")
	@ResponseBody
	ResponseEntity<Long> getStationAsReturn(@RequestParam String id){
			long count = journeyService.getStationAsReturn(id);
		return ResponseEntity.ok().body(count);
	}
	
	@PostMapping("api/1.0/journeys/add")
	@ResponseStatus(HttpStatus.OK)
	public GenericResponse addJourney(@RequestBody Journey journey) {
		journeyService.save(journey);
		return new  GenericResponse("Station created");
	}
		
	@GetMapping("/api/1.0/journeys")
	Page<Journey> getJourneys(Pageable page){
		
		return journeyService.getJourneys(page);
	}
	
	@GetMapping("/api/1.0/journeys/departure/average")
	String getAverageDistanceAsDeparture(@RequestParam String id){
		return journeyService.averageDistanceAsDeparture(id);
	}
	
	@GetMapping("/api/1.0/journeys/return/average")
	String getAverageDistanceAsReturn(@RequestParam String id){
		return journeyService.averageDistanceAsReturn(id);
	}
	
	
	@GetMapping("/api/1.0/journeys/total")
	long getTotalCoveredDistance(){
		return journeyService.totalCoveredDistance();
	}
	
	@GetMapping("/api/1.0/journeys/average")
	String getAverageDistanceCovered(){
		DecimalFormat df = new DecimalFormat("#.##");      
		return df.format(journeyService.averageDistanceCovered());
	}
	
	@GetMapping("/api/1.0/journeys/count")
	long getJourneysCount(){
		return journeyService.count();
	}
	
	

}
