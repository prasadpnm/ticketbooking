package com.prasad.ticketbooking.service.impl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.prasad.ticketbooking.jpa.Programs;
import com.prasad.ticketbooking.jpa.ReservationRepository;
import com.prasad.ticketbooking.jpa.Seats;
import com.prasad.ticketbooking.jpa.SeatsRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class SeatlayoutTests {

	// @Mock
	// Optional<Programs> program;
	@Mock
	SeatsRepository repository;
	@Mock
	ReservationRepository reservationRepo;
	@Mock
	SeatLayout layout;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		layout = new SeatLayout();
		Programs programObj = new Programs("first show");
		// when(program.get()).thenReturn(programObj);
		List<Seats> seats = new ArrayList<Seats>();
		List<Seats> emptySeats = new ArrayList<Seats>();
		for (int i = 1; i <= 20; i++) {

			for (int j = 1; j <= 35; j++) {
				Seats seat = new Seats(i, j);
				seats.add(seat);
			}
		}

		when(repository.getAllSeats(programObj)).thenReturn(seats);
		when(reservationRepo.getReservedSeats(programObj)).thenReturn(emptySeats);
		layout.intilize(programObj, repository, reservationRepo);

	}

	@Test
	public void testSeatLayoutToString_35_200() {

		System.out.println(layout.toString());

		for (int i = 1; i <=200; i++) {
			int randomNumber = getRandomNumber(35, 1);
			String email = "Prasad" + i + "@gmail.com";

			layout.holdBestAvialbleSeats(email, randomNumber);

			System.out.println(i+"After reservation for :" + email +"::Number of Seats :"+randomNumber+"::Available Seats:"+layout.getAvailableSeats());
			System.out.println(layout.toString());
		}

	}
	

	@Test
	public void testSeatLayoutToString_1_700() {

		System.out.println(layout.toString());

		for (int i = 1; i <=700; i++) {
			int randomNumber = 1;
			String email = "Prasad" + i + "@gmail.com";

			layout.holdBestAvialbleSeats(email, randomNumber);

			System.out.println(i+"After reservation for :" + email +"::Number of Seats :"+randomNumber+"::Available Seats:"+layout.getAvailableSeats());
			System.out.println(layout.toString());
		}

	}
	
	@Test
	public void testSeatLayoutToString_35_100() {

		System.out.println(layout.toString());

		for (int i = 1; i <=100; i++) {
			int randomNumber =  getRandomNumber(50, 1);
			String email = "Prasad" + i + "@gmail.com";

			layout.holdBestAvialbleSeats(email, randomNumber);

			System.out.println(i+"After reservation for :" + email +"::Number of Seats :"+randomNumber+"::Available Seats:"+layout.getAvailableSeats());
			System.out.println(layout.toString());
		}

	}
	
	@Test
	public void testSeatLayoutToString_50_700() {

		System.out.println(layout.toString());

		for (int i = 1; i <=700; i++) {
			int randomNumber =  getRandomNumber(50, 1);
			String email = "Prasad" + i + "@gmail.com";

			layout.holdBestAvialbleSeats(email, randomNumber);

			System.out.println(i+"After reservation for :" + email +"::Number of Seats :"+randomNumber+"::Available Seats:"+layout.getAvailableSeats());
			System.out.println(layout.toString());
		}

	}


	private int getRandomNumber(int maximum, int minimum) {
		return ThreadLocalRandom.current().nextInt(minimum, maximum + 1);
	
	}

}
