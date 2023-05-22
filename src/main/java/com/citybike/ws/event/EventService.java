package com.citybike.ws.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {

	@Autowired
	EventRepository eventRepository;
	
	Page<Event> getEvents(Pageable page) {
		return eventRepository.findAll(page);
	}

	public void  createEvent(Event event) {
		 eventRepository.save(event);
	}

}
