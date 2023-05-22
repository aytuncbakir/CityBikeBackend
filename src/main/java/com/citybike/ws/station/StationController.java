package com.citybike.ws.station;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.citybike.ws.journey.JourneyService;
import com.citybike.ws.shared.GenericResponse;

@RestController
public class StationController {
	
	@Autowired
	StationService stationService;
	
	@Autowired
	JourneyService journeyService;
	
	@GetMapping("/api/1.0/stations")
	Page<Station> getStations(Pageable page){
		return stationService.getStations(page);
	}
	
	@PostMapping("api/1.0/stations/add")
	@ResponseStatus(HttpStatus.OK)
	public GenericResponse addStation(@RequestBody Station station) {
		stationService.save(station);
		return new  GenericResponse("Station created");
	}
	
	@GetMapping("/api/1.0/stations/favorite/departures")
	ResponseEntity<List<String>> getFavoriteDepartureStations(){
		return ResponseEntity.ok( journeyService.getFavoriteDepartureStations());
	}
	
	@GetMapping("/api/1.0/stations/favorite/returns")
	ResponseEntity<List<String>> getFavoriteReturnStations(){
		return ResponseEntity.ok( journeyService.getFavoriteReturnStations());
	}
	
	@GetMapping("/api/1.0/stations/count")
	public long count() {
		return stationService.count();
	}	
	
}
