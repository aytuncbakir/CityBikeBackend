package com.citybike.ws.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository  extends JpaRepository<Event, Integer>{
	

}