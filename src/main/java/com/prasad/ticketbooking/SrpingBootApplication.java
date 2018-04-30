package com.prasad.ticketbooking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.prasad.ticketbooking.jpa.CustomerRepository;
import com.prasad.ticketbooking.jpa.ProgramsRepository;
import com.prasad.ticketbooking.jpa.ReservationRepository;
import com.prasad.ticketbooking.jpa.SeatsRepository;
@SpringBootApplication 
public class SrpingBootApplication {
	
	private static final Logger log = LoggerFactory.getLogger(SrpingBootApplication.class);

	  public static void main(String[] args) throws Exception {
	    	
	    	System.out.println("****************Logger Path**************** :}"+System.getProperty("java.io.tmpdir"));
	        SpringApplication.run(SrpingBootApplication.class, args);
	    }
	  
	  
	  //quick test...
	  @Bean
		public CommandLineRunner demo(SeatsRepository repository,ProgramsRepository programRepository,ReservationRepository reservationrepo,CustomerRepository customerrepository) {
			return (args) -> {
				
//				Integer rowid=9;
//				Integer seatNo=9;
//				Customer customer=customerrepository.findByEmail("prasad.msc@gmail.com");
//				if(customer==null) {
//					customer=new Customer("prasad.msc@gmail.com");
//					customer=customerrepository.save(customer);
//				}
//				//List<Seats> seats=repository.getReservedSeats(programRepository.findById(new Long(1)).get());
//				Seats seat=repository.findByrowidAndSeatno(rowid, seatNo);
//				List<Seats> seats=new ArrayList<Seats>();
//				Reservation reservation =new Reservation(seats,programRepository.findById(1L).get(),customer,"success");
//				reservation=reservationrepo.save(reservation);
//		
//				
//				System.out.println(reservation);
//				reservationrepo.delete(reservation);
//				
//				System.out.println(seat.getRowid());
//				for(Seats seat:seats) {
//					System.out.println(seat.getRowid()+":"+seat.getSeatno());
//				}
				// save a couple of customers
//				for( short i=1;i<=20;i++) {
//					for(short j=1;j<=35;j++) {
//						repository.save(new Seats(i,j));
//					}
//				}
			};
		}
	  
	
}
