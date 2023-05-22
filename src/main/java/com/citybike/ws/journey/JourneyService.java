package com.citybike.ws.journey;


import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JourneyService {
	
	@Autowired
	JourneyRepository journeyRepository;
	
	private static final DecimalFormat df = new DecimalFormat("0.00");

	public Page<Journey> getJourneys(Pageable page) {
		return journeyRepository.findAll(page);
	}

	public long getStationAsDeparture(String id) {
		return journeyRepository.countByDepartureStationId(id);
	}
	
	public long getStationAsReturn(String id) {
		return journeyRepository.countByReturnStationId(id);
	}

	public List<String> getFavoriteDepartureStations() {
		return journeyRepository.findFavoriteDepartureStations();
		
	}
	
	public List<String> getFavoriteReturnStations() {
		return journeyRepository.findFavoriteReturnStations();
	}
	
	public String averageDistanceAsDeparture(String id) {
		return  df.format(journeyRepository.averageDistanceAsDeparture(id));
	}
	
	public String averageDistanceAsReturn(String id) {
		return  df.format(journeyRepository.averageDistanceAsReturn(id));
	}

	public void save(Journey journey) {
		journeyRepository.save(journey);
	}
	
	public long totalCoveredDistance() {
		return journeyRepository.totalCoveredDistance();
	}
	
	public double averageDistanceCovered() {
		return journeyRepository.averageDistanceCovered();
	}

	public long count() {
		
		return journeyRepository.count();
	}
	
	

}
