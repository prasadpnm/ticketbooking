package com.prasad.ticketbooking.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prasad.ticketbooking.jpa.Programs;
import com.prasad.ticketbooking.jpa.ReservationRepository;
import com.prasad.ticketbooking.jpa.Seats;
import com.prasad.ticketbooking.jpa.SeatsRepository;
import com.prasad.ticketbooking.model.Seat;
import com.prasad.ticketbooking.model.SeatHold;

public class SeatLayout {

	private int availableSeats = 0;

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	Map<Integer, Integer> rowAvailable = Collections.synchronizedMap( new HashMap<Integer, Integer>());
	Map<Integer, List<Boolean>> rowSeats = Collections.synchronizedMap(new HashMap<Integer, List<Boolean>>());

	/**
	 * 
	 * @param optional
	 * @param repository
	 * @param reservationRepo 
	 * @return
	 */
	public synchronized boolean intilize(Programs program, SeatsRepository repository, ReservationRepository reservationRepo) {
		List<Seats> seats = repository.getAllSeats(program);

		this.availableSeats = seats.size();

		for (Seats seat : seats) {
			if (rowSeats.get(seat.getRowid()) != null) {
				rowSeats.get(seat.getRowid()).add(Boolean.FALSE);
			} else {
				List<Boolean> seatList = new ArrayList<>();
				seatList.add(Boolean.FALSE);
				rowSeats.put(seat.getRowid(), seatList);
			}

		}

		for (Integer key : rowSeats.keySet()) {
			rowAvailable.put(key, rowSeats.get(key).size());

		}

		List<Seats> reservedSeats =reservationRepo.getReservedSeats(program);

		for (Seats seat : reservedSeats) {
			if (rowSeats.get(seat.getRowid()) != null) {
				rowSeats.get(seat.getRowid()).add(seat.getSeatno() - 1, Boolean.TRUE);
				rowAvailable.put(seat.getRowid(), rowAvailable.get(seat.getRowid()) - 1);
			}
		}
		this.availableSeats = this.availableSeats - reservedSeats.size();
		return false;

	}

	/**
	 * this method Allocates seats in two different ways. 1. identity the any
	 * continuous seats for the given Number of Seats starting from the stage. 2. if
	 * the first option is not possible allocate seats based on the available
	 * location starting from the stage.
	 * 
	 * @param email
	 * @param numofSeats
	 * @return
	 */
	public synchronized SeatHold holdBestAvialbleSeats(String email, int numofSeats) {
		// 1st Option
		for (int i = 1; i <= rowAvailable.size(); i++) {
			if (rowAvailable.get(i) >= numofSeats) {

				List<Integer> selectedSeats = getContinuousSeatsInArow(rowSeats.get(i), numofSeats);
				if (selectedSeats != null && selectedSeats.size() == numofSeats) {
					rowAvailable.put(i, rowAvailable.get(i) - numofSeats);
					this.availableSeats = this.availableSeats - numofSeats;
					return pupulateSeats(i, selectedSeats, email);

				}
			}

		}

		// 2nd Option

		Map<Integer, List<Integer>> setsmap =Collections.synchronizedMap( new HashMap<Integer, List<Integer>>());
		int tempNoOfSeats = numofSeats;

		for (int i = 1; i <= rowAvailable.size(); i++) {

			if (rowAvailable.get(i) > 0 && tempNoOfSeats != 0) {
				List<Integer> selectedSeats = getAnyAvailableSeats(rowSeats.get(i), tempNoOfSeats);
				setsmap.put(i, selectedSeats);
				tempNoOfSeats = tempNoOfSeats - selectedSeats.size();
				if (tempNoOfSeats == 0) {
					this.availableSeats = this.availableSeats - numofSeats;
					return pupulate2DSeats(i, setsmap, email);
				}

			}

		}

		return null;

	}

	/**
	 * update global seats data structure and prepare seatHold object.
	 * 
	 * @param i
	 * @param setsmap
	 * @param email
	 * @return
	 */
	private SeatHold pupulate2DSeats(int i, Map<Integer, List<Integer>> setsmap, String email) {
		SeatHold hold = new SeatHold();
		List<Seat> seats = new ArrayList<Seat>();
		hold.setCustomerEmail(email);

		for (Integer row : setsmap.keySet()) {
			List<Boolean> list = rowSeats.get(row);
			for (Integer col : setsmap.get(row)) {
				list.set(col - 1, Boolean.TRUE);
				Seat seat = new Seat(row, col.intValue());
				seats.add(seat);
			}
			rowAvailable.put(i, rowAvailable.get(i) - setsmap.get(row).size());
		}
		hold.setSelectedSeats(seats);

		return hold;
	}

	private List<Integer> getAnyAvailableSeats(List<Boolean> list, int noOfSeats) {
		List<Integer> seats = new ArrayList<Integer>(noOfSeats);
		int counter = 0;
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i)) {
				counter++;
				seats.add(i+1);

			}
			if (counter == noOfSeats) {
				break;
			}
		}

		return seats;
	}

	/**
	 * 
	 * @param row
	 * @param seatNos
	 * @param email
	 * @return
	 */
	private SeatHold pupulateSeats(int row, List<Integer> seatNos, String email) {
		SeatHold hold = new SeatHold();
		List<Seat> seats = new ArrayList<Seat>(seatNos.size());
		hold.setCustomerEmail(email);
		for (Integer seatNo : seatNos) {
			Seat seat = new Seat(row, seatNo.intValue());
			seats.add(seat);
		}
		hold.setSelectedSeats(seats);

		return hold;

	}

	/**
	 * Continuous seat allocation.
	 * 
	 * @param list
	 * @param numofSeats
	 * @return
	 */
	private List<Integer> getContinuousSeatsInArow(List<Boolean> list, int numofSeats) {
		List<Integer> selectedSeats = new ArrayList<Integer>();
		int start = 0;
		int end = 0;
		int fullsize=list.size();
		int listMiddleVal = list.size() / 2;
		int counter = 0;
		// 1st Option if the group greater than half size of the row assign from left to
		// right
		counter=0;
		start=0;
		end=0;
		if (numofSeats > listMiddleVal) {
			for (int i = 0; i < list.size(); i++) {

				if (!list.get(i)) {
					if (counter == 0) {
						start = i;
						end = i;
						counter = 1;
					} else {
						end = i;
						counter = counter + 1;
					}
					if (counter == numofSeats) {

						for (int j = start; j <= end; j++) {
							list.set(j, Boolean.TRUE);
							selectedSeats.add(j + 1);

						}
						return selectedSeats;
					}
				} else {
					counter = 0;
					start = 0;
					end = 0;
				}
			

			}
		}

		// 2nd Option search from middle to first.
		counter=0;
		start=0;
		end=0;
		for (int i = listMiddleVal; i >= 0; i--) {

			if (!list.get(i)) {
				if (counter == 0) {
					start = i;
					end = i;
					counter = 1;
				} else {
					end = i;
					counter = counter + 1;
				}
				if (counter == numofSeats) {

					for (int j = start; j >= end; j--) {
						list.set(j, Boolean.TRUE);
						selectedSeats.add(j + 1);

					}
					return selectedSeats;
				}
			} else {
				counter = 0;
				start = 0;
				end = 0;
			}
			

		}
		// 3rd Option search from Middle to last.
		counter=0;
		start=0;
		end=0;
		for (int i = listMiddleVal + 1; i <list.size(); i++) {

			if (!list.get(i)) {
				if (counter == 0) {
					start = i;
					end = i;
					counter = 1;
				} else {
					end = i;
					counter = counter + 1;
				}
				if (counter == numofSeats) {

					for (int j = start; j <= end; j++) {
						list.set(j, Boolean.TRUE);
						selectedSeats.add(j + 1);

					}
					return selectedSeats;
				}

			} else {
				counter = 0;
				start = 0;
				end = 0;
			}
			
		}

		return selectedSeats;
	}

	/**
	 * This method is used to cancel the seat Layout.
	 * 
	 * @param seatHold
	 * @return
	 */
	public boolean cancelSeatHold(SeatHold seatHold) {

		// TODO future development
		boolean isSuccess = true;

		List<Seat> seatsToCancel = seatHold.getSelectedSeats();

		int totalSeats = seatsToCancel.size();

		Map<Integer, List<Integer>> seatsTocancelMap = new HashMap<Integer, List<Integer>>();

		for (Seat seat : seatsToCancel) {

			if (seatsTocancelMap.get(seat.getRow()) != null) {
				seatsTocancelMap.get(seat.getRow()).add(seat.getCol() - 1);
			} else {
				List<Integer> seats = new ArrayList<Integer>();
				seats.add(seat.getCol() - 1);
				seatsTocancelMap.put(seat.getRow(), seats);
			}

		}

		for (Integer row : seatsTocancelMap.keySet()) {
			List<Boolean> masterList = rowSeats.get(row);
			for (Integer col : seatsTocancelMap.get(row)) {
				masterList.set(col, Boolean.FALSE);

			}
			rowAvailable.put(row, rowAvailable.get(row) + seatsTocancelMap.get(row).size());
		}
		this.availableSeats = this.availableSeats + totalSeats;

		return isSuccess;

	}

	/**
	 * 
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		for (Integer integer : rowSeats.keySet()) {

			buffer.append("Available =");
			buffer.append(rowAvailable.get(integer));
			buffer.append(" ");
			for (Boolean seat : rowSeats.get(integer)) {
				if (!seat) {
					buffer.append("Y");
				} else {
					buffer.append("N");
				}

			}
			buffer.append("\n");

		}
		return buffer.toString();

	}

}
