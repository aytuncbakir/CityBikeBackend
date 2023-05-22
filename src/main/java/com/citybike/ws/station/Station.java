package com.citybike.ws.station;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stations")
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long fid;
	
	private String id;
	private String nimi;
	private String namn;
	private String name;
	private String osoite;
	private String address;
	private String kaupunki;
	private String stad;
	private String operaattori;
	private int kapasiteet;
	private double longitude;
	private double latitude;

}
