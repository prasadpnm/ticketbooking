package com.prasad.ticketbooking.service.impl;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.prasad.ticketbooking.model.SeatHold;

/**
 * this Holds temporary Cache. it holds seatHoldObject.
 */

@Service
public class SessionHandler {
	// Timer timer;
	private static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);

	private static Map<String, SeatHold> map = new ConcurrentHashMap<String, SeatHold>();
	private static Map<String, Timer> timerMap = new ConcurrentHashMap<String, Timer>();

	public boolean addSeatHoldObject(SeatHold seatHold,SeatLayout layout) {
		logger.debug("-----start addSeatHold object-----");
		boolean isSuccess = true;
		Timer timer = new Timer();
		RemindTask task = new RemindTask();
		task.setEmail(seatHold.getCustomerEmail());
		task.setTimer(timer);
		task.setLayout(layout);
		timer.schedule(task, 300 * 1000);
		String sessionId=UUID.randomUUID().toString();
		map.put(seatHold.getCustomerEmail(), seatHold);
		timerMap.put(seatHold.getCustomerEmail(),timer);
		logger.debug("-----end addSeatHold object-----");
		return isSuccess;
	}
	
	public SeatHold removetimer(String email,SeatLayout seatHolder) {
		SeatHold seatHold=null;
		logger.debug("-----start removetimer object-----");
		timerMap.get(map.get(email).getCustomerEmail()).cancel();
		 seatHold=map.remove(email);
		//seatHolder.cancelSeatHold(seatHold);
		logger.debug("-----end removetimer object-----");
		return seatHold;
	}


	class RemindTask extends TimerTask {
		String email;
		Timer timer;
		SeatLayout layout;

		public SeatLayout getLayout() {
			return layout;
		}

		public void setLayout(SeatLayout layout) {
			this.layout = layout;
		}

		public Timer getTimer() {
			return timer;
		}

		public void setTimer(Timer timer) {
			this.timer = timer;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void run() {
			logger.debug("Time's up for the Email :{}", this.email);
			SeatHold seatHold=map.remove(this.email);
			this.layout.cancelSeatHold(seatHold);
			logger.debug("Cache Size :{}",map.size());
			this.timer.cancel(); // Terminate the timer thread
		}
	}

}
