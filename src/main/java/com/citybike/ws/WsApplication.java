package com.citybike.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.citybike.ws.user.UserService;
import com.citybike.ws.blog.BlogService;

import com.citybike.ws.event.EventService;

//import com.citybike.ws.event.Event;
//import com.citybike.ws.blog.Blog;
//import com.citybike.ws.blog.vm.BlogSubmitVM;
//import com.citybike.ws.user.User;

/**
 * @author aytunc bakir
 *
 */
@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}
	
	/**
	 * This method provides dummy data of users and their blogs
	 * @param userService
	 * @param blogService
	 * @return
	 */
	@Bean
	CommandLineRunner createInitialUser (UserService userService, BlogService blogService, EventService eventService) {
		return (args) -> {
//			for(int i = 1 ; i<=25; i++) {
//				User user = new User();
//				user.setUsername("user"+i);
//				user.setDisplayName("display"+i);
//				user.setPassword("P4ssword");
//				userService.save(user);
//				for(int j = 1 ; j<=20; j++) {
//					BlogSubmitVM blog = new BlogSubmitVM();
//					blog.setContent("blog"+ j + " from user"+i);
//					blogService.save(blog, user);
//				}
//			}
			
//			String[] eventNames = {"Sanna", "Anna", "Miko", "Jounu", "Tuula", "Tuija", "Aija","Niko", "Timo", "Teemu", 
//									"Sirkka", "Kaisa", "Anne", "Viljamin", "Esa", "Essi", "Marita", "Elena", "Salo","Mika",
//									"Juho", "Pekka", "Tauno", "Sari", "Kari"};
//			
//			String[] eventDescriptions = {"Kesä Bike", "Toukokuu Bike", "Metsä Bike", "Vuori Bike", "Elokuu Bike", "Heinäkuu Bike", "Espoo Bike",
//					"Niko", "Timo", "Teemu", 
//					"Vanta Bike", "Helsinki Bike", "Turku Bike", "Miesten Päivä", "Naisten Päivä", "Essi Bike", "Maritan Bike", "Elenan Bike", 
//					"Salo Bike","Mika bike",
//					"Juhon Bike", "Pekka Bike", "Tauno Bike ", "Sari Bike", "Kari"};
//			
//			String[] eventAddresses = {"Lahti", "Rauma", "Oulu", "Jyväskyla", "Kouvola", "Lappeenranta", "Imatra","Helsinki", "Rovaniemi", "Kemi", 
//					"Tampere", "Turku", "Nokia", "Salo", "Loima", "Vaasa", "Seinajoki", "Ilmajoki", "Kotka","Joensu",
//					"Kuopio", "Hämeenlinna", "Pori", "Porvo", "Espoo"};
			
//			for(int i = 1 ; i<=25; i++) {
//				Event event = new Event();
//				event.setName(eventNames[i-1]);
//				event.setCapacity(i + 5);
//				event.setAddress(eventAddresses[i-1]);
//				event.setDescription(eventDescriptions[i-1]);
//				event.setDate("05-0"+i+"-2023");
//				event.setImage("image"+i);
//				eventService.createEvent(event);
//			}
		};
	}
}