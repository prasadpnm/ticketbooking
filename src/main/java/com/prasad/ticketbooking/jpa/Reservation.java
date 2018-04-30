package com.prasad.ticketbooking.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	 @OneToMany(
		        cascade = CascadeType.ALL, 
		        orphanRemoval = true
		    )
		    private List<Seats> seats = new ArrayList<Seats>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	private Programs programs;

	private String status;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CID")
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Reservation() {}

	public Reservation(List<Seats> seats,Programs programs,Customer customer,String status) {
		
		this.seats=seats;
		this.programs=programs;
		this.customer=customer;
		this.status=status;
		
	}
	
	

	public List<Seats> getSeats() {
		return seats;
	}

	public void setSeats(List<Seats> seats) {
		this.seats = seats;
	}

	public Programs getPrograms() {
		return programs;
	}

	public void setPrograms(Programs programs) {
		this.programs = programs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
