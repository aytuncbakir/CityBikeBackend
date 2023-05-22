package com.citybike.ws.journey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JourneyRepository  extends JpaRepository<Journey, Long>{
	
    long countByDepartureStationId(String id);
    long countByReturnStationId(String id);
    
    @Query(value = "SELECT departure_station_name,  COUNT(*) AS count FROM journeys "
    				+ "GROUP BY departure_station_name "
    				+ "ORDER BY count DESC Limit  5",  nativeQuery = true)
	List<String> findFavoriteDepartureStations();
    
    @Query(value = "SELECT return_station_name,  COUNT(*) AS count FROM journeys "
    				+ "GROUP BY return_station_name "
    				+ "ORDER BY count DESC Limit  5",  nativeQuery = true)
	List<String> findFavoriteReturnStations();
    
    @Query(value = "SELECT AVG(covered_distance) AS average FROM journeys WHERE departure_station_id = :id",  nativeQuery = true)
    double averageDistanceAsDeparture(@Param("id") String id);
    
    @Query(value= "SELECT AVG(covered_distance) AS average FROM journeys WHERE return_station_id = :id",  nativeQuery = true)
    double averageDistanceAsReturn(@Param("id") String id);
    
    
    @Query(value= "SELECT SUM(covered_distance) AS covered FROM journeys",  nativeQuery = true)
    long totalCoveredDistance();
    
    @Query(value= "SELECT AVG(covered_distance) AS average FROM journeys",  nativeQuery = true)
    double averageDistanceCovered();
    

}

