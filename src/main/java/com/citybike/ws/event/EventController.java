package com.citybike.ws.event;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.citybike.ws.shared.GenericResponse;

@RestController
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@GetMapping("/api/1.0/events")
	Page<Event> getEvents(Pageable page){
		return eventService.getEvents(page);
	}
	
	@PostMapping("/api/1.0/events/add")
	public GenericResponse createEvent(@RequestBody Event event){
		eventService.createEvent(event);
		return new  GenericResponse("Event created");
	}
	
	
	
//	

}
