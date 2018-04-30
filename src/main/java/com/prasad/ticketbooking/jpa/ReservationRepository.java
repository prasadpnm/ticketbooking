package com.prasad.ticketbooking.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	
//	@Query ("select count(*) from Seats s where s.id not in (select r.id from Reservation r where r.programs=?1)")
//	int getAvailableSeats( Long id);
//	
	
	@Query ("select r.seats from Reservation r where r.programs=?1")
	List<Seats> getReservedSeats(Programs programID);
	

}
