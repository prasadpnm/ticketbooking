package com.prasad.ticketbooking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prasad.ticketbooking.jpa.Customer;
import com.prasad.ticketbooking.jpa.CustomerRepository;
import com.prasad.ticketbooking.jpa.ProgramsRepository;
import com.prasad.ticketbooking.jpa.Reservation;
import com.prasad.ticketbooking.jpa.ReservationRepository;
import com.prasad.ticketbooking.jpa.Seats;
import com.prasad.ticketbooking.jpa.SeatsRepository;
import com.prasad.ticketbooking.model.Seat;
import com.prasad.ticketbooking.model.SeatHold;
import com.prasad.ticketbooking.service.TicketService;


@Service
public class TicketServiceImpl implements TicketService {

	private Map<Integer, SeatLayout> programHolder = new ConcurrentHashMap<Integer, SeatLayout>();

	@Autowired
	SeatsRepository repository;
	@Autowired
	ProgramsRepository programRepository;
	@Autowired
	ReservationRepository reservationRepo;
	@Autowired
	CustomerRepository customerRepository;
	// TODO this can prameterize to support more programs ticket booking at a time.
	// booking.
	private int programCode = 1;
	@Autowired
	SessionHandler sessionHandler;


	@Override
	public int numSeatsAvailable() {
		if (programHolder.get(programCode) != null) {
			return programHolder.get(programCode).getAvailableSeats();
		} else {
			SeatLayout layout = new SeatLayout();
			layout.intilize(programRepository.findById(1L).get(), repository,reservationRepo);
			programHolder.put(programCode, layout);
			return layout.getAvailableSeats();
		}
	}


	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		SeatHold seatHold = null;
		SeatLayout layout = null;
		if (programHolder.get(programCode) != null) {
			layout = programHolder.get(programCode);
			seatHold = layout.holdBestAvialbleSeats(customerEmail, numSeats);
		} else {

			throw new RuntimeException("Exception!!! Please check the availability before reservation.");
			// layout=new SeatLayout();
			// layout.intilize(programCode);
			// programHolder.put(programCode, layout);
			// seatHold=programHolder.get(programCode).holdBestAvialbleSeats(customerEmail,
			// numSeats);
			//
		}
		sessionHandler.addSeatHoldObject(seatHold, layout);
		return seatHold;
	}

	@Override
	@Transactional
	public String reserveSeats(int seatHoldId, String customerEmail) {
		String status = "success";
		SeatLayout layout = programHolder.get(programCode);
		if (layout == null) {
			throw new RuntimeException("Exception occured.. Please reserve seat once again.");
		}
		SeatHold seatHold = sessionHandler.removetimer(customerEmail, layout);
		if (seatHold == null) {
			throw new RuntimeException("Exception occured.. Please reserve seat once again.");
		}
		List<Seats> sqlseats = new ArrayList<Seats>();

		for (Seat seat : seatHold.getSelectedSeats()) {
			sqlseats.add(repository.findByrowidAndSeatno(seat.getRow(), seat.getCol()));

		}
		
		Customer customer=customerRepository.findByEmail(customerEmail);
		if(customer==null) {
			customer=new Customer(customerEmail);
			customer=customerRepository.save(customer);
		}
		Reservation reservation = new Reservation(sqlseats, programRepository.findById(1L).get(),customer, "Success");
		reservationRepo.save(reservation);
		return status;
	}

}
