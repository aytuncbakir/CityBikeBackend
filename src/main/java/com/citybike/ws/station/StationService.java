package com.citybike.ws.station;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StationService {
	
	@Autowired
	StationRepository stationRepository;

	public Page<Station> getStations(Pageable page) {
		return stationRepository.findAll(page);
	}

	public void save(Station station) {
		 stationRepository.save(station);
		
	}

	public long count() {
		return stationRepository.count();
	}

}
