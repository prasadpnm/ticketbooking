package com.prasad.ticketbooking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ticketbooking.model.SeatHold;
import com.prasad.ticketbooking.model.SeatReservationRequest;
import com.prasad.ticketbooking.model.ConfirmRequest;
import com.prasad.ticketbooking.model.ConfirmResponse;
import com.prasad.ticketbooking.service.TicketService;

@RestController
@EnableAutoConfiguration
public class TicketRestController extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(TicketRestController.class);

	@Autowired
	TicketService ticketService;

	@RequestMapping(value = "/noOfSeatsAvlble", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Integer> getTotalAvlbleSeats() {
		logger.debug("---Start-- getTotalAvlbleSeats---- ");
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		int noOfSeatsAvlble = ticketService.numSeatsAvailable();
		logger.debug("Total Available Seats Found.{}", noOfSeatsAvlble);
		logger.debug("---End-- getTotalAvlbleSeats---- ");

		return new ResponseEntity<Integer>(Integer.valueOf(noOfSeatsAvlble), httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/reservseats", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<SeatHold> reserveBestAvailableSeats(@RequestBody SeatReservationRequest request) {
		logger.debug("---Start-- getTotalAvlbleSeats---- ");
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		SeatHold seatHold = ticketService.findAndHoldSeats(request.getNumberOfSeats(), request.getEmail());
		logger.debug("Total Seats selected.{}", seatHold.getSelectedSeats().size());
		logger.debug("---End-- getTotalAvlbleSeats---- ");
		return new ResponseEntity<SeatHold>(seatHold, httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/conformReservation", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<ConfirmResponse> confirmReservation(@RequestBody ConfirmRequest confirmRequest) {
		ConfirmResponse response=new ConfirmResponse();
		String status = "success";
		logger.debug("---Start-- getTotalAvlbleSeats---- ");
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		// TODO session id need to be implemented.
		status = ticketService.reserveSeats(1, confirmRequest.getEmail());
		logger.debug("API return Status.{}", status);
		logger.debug("---End-- getTotalAvlbleSeats---- ");
		response.setStatus(status);
		return new ResponseEntity<ConfirmResponse>(response, httpHeaders, HttpStatus.OK);
	}

}
