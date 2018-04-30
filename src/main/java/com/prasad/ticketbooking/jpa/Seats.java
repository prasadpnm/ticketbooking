package com.prasad.ticketbooking.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Seats {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Integer rowid;
    private Integer seatno;
    
    protected Seats() {}
    
    public Integer getSeatno() {
		return seatno;
	}

	public void setSeatno(Integer seatno) {
		this.seatno = seatno;
	}

	public Seats(Integer rowid,Integer seatno) {
    	this.setRowid(rowid);
    	this.seatno=seatno;
    }

	public Integer getRowid() {
		return rowid;
	}

	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}
    
}
