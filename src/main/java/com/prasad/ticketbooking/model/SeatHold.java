package com.prasad.ticketbooking.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeatHold implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerEmail;
	private List<Seat> selectedSeats;
	private Integer programCode;
	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public List<Seat> getSelectedSeats() {
		if (selectedSeats == null) {
			{
				selectedSeats = new ArrayList<Seat>();
			}
			return Collections.unmodifiableList(selectedSeats);
		}
		return selectedSeats;
	}

	public void setSelectedSeats(List<Seat> selectedSeats) {
		if (this.selectedSeats == null) {
			this.selectedSeats = new ArrayList<Seat>();
			this.selectedSeats.addAll(selectedSeats);
		}else {
		this.selectedSeats.addAll(selectedSeats);
		}
	}

	public Integer getProgramCode() {
		return programCode;
	}

	public void setProgramCode(Integer programCode) {
		this.programCode = programCode;
	}

}
