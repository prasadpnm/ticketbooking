package com.prasad.ticketbooking.model;

import java.io.Serializable;

public class Seat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Seat(Integer row, Integer col) {
		this.row = row;
		this.col = col;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getCol() {
		return col;
	}

	public void setCol(Integer col) {
		this.col = col;
	}

	Integer row;
	Integer col;

}
