package com.projects.eventticket.eventticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;

@SpringBootApplication
public class EventticketApplication {

	public static void main(String[] args) {
		ZoneId zone = ZoneId.systemDefault();
		System.out.println("Current ZoneId is: " + zone.getId());

		SpringApplication.run(EventticketApplication.class, args);
	}

}
