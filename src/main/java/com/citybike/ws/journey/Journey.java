package com.citybike.ws.journey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "journeys")
public class Journey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String departure;
	private String returnn;
	
	@Column(name="departure_station_id")
	private String departureStationId;
	
	@Column(name="departure_station_name")
	private String departureStationName;
	
	@Column(name="return_station_id")
	private String returnStationId;
	
	@Column(name="return_station_name")
	private String returnStationName;
	
	@Column(name="covered_distance")
	private double coveredDistance;
	private int duration;

}
