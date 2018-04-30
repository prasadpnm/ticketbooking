package com.prasad.ticketbooking.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SeatsRepository  extends Repository<Seats, Long> {
	
	@Query ("from Seats s where s.id  in (select r.id from Reservation r where r.programs=?1) order by rowid,seatno asc")
	List<Seats> getReservedSeats( Programs programID);
	
	@Query ("from Seats s order by rowid,seatno asc")
	List<Seats> getAllSeats( Programs programID);
	
	
	Seats findByrowidAndSeatno(Integer rowid,Integer seatno);

}
