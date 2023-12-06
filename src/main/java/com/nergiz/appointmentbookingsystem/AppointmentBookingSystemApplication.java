package com.nergiz.appointmentbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AppointmentBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentBookingSystemApplication.class, args);
	}

}
