package com.moviebookingapp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebookingapp.Repository.MoviesRepo;
import com.moviebookingapp.Repository.TicketRepo;
import com.moviebookingapp.Repository.UserRepo;
import com.moviebookingapp.Service.TicketService;
import com.moviebookingapp.exception.MovieNotAvailableException;
import com.moviebookingapp.exception.NoTicketBookedException;
import com.moviebookingapp.exception.UserNotExistException;
import com.moviebookingapp.models.Movies;
import com.moviebookingapp.models.Tickets;
import com.moviebookingapp.models.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService{

	private TicketRepo ticketRepo;
	@Autowired
	private MoviesRepo moviesRepo;
	@Autowired
	private UserRepo userRepo;

	public TicketServiceImpl( TicketRepo ticketRepo) {
		
		this.ticketRepo=ticketRepo;
	}
	
	@Override
	public String bookTickets(Tickets tickets) throws MovieNotAvailableException, UserNotExistException{
		//Tickets ticket= ticketRepo.save(tickets);
		log.info("searching movie by name");
		boolean user=userRepo.existsById(tickets.getUserid());
		if(user) {
		Movies movie =moviesRepo.findBymovieName(tickets.getMovie().getKey().getMovieName(),tickets.getMovie().getKey().getTheatreName());
		if(movie==null) {
			log.error("no movie found with "+tickets.getMovie().getKey().getMovieName());
			throw new MovieNotAvailableException("Movie not available");
		}
		else {
		if(movie.getTotalNoOfTickets()-tickets.getNoOfTickets()>=0) {
			log.info("updating total no. of tickets of "+tickets.getMovie().getKey().getMovieName());
			movie.setTotalNoOfTickets(movie.getTotalNoOfTickets()-tickets.getNoOfTickets());
			moviesRepo.save(movie);
			log.info("ticket booked successfully");
			ticketRepo.save(tickets);
			return "Ticket Booked";
		}
		else {
			
			return "Sold Out";
		}
		}
		}
		else {
			throw new UserNotExistException("user not exist");
			
		}
		
	}
//	@Override
//	public String bookTickets(Tickets tickets) throws MovieNotAvailableException{
//		//Tickets ticket= ticketRepo.save(tickets);
//		log.info("searching movie by name");
//		
//		Movies movie =moviesRepo.findBymovieName(tickets.getMovie().getKey().getMovieName(),tickets.getMovie().getKey().getTheatreName());
//		if(movie==null) {
//			log.error("no movie found with "+tickets.getMovie().getKey().getMovieName());
//			throw new MovieNotAvailableException("Movie not available");
//		}
//		else {
//		if(movie.getTotalNoOfTickets()-tickets.getNoOfTickets()>=0) {
//			log.info("updating total no. of tickets of "+tickets.getMovie().getKey().getMovieName());
//			movie.setTotalNoOfTickets(movie.getTotalNoOfTickets()-tickets.getNoOfTickets());
//			moviesRepo.save(movie);
//			log.info("ticket booked successfully");
//			ticketRepo.save(tickets);
//			return "Ticket Booked";
//		}
//		else {
//			
//			return "Sold Out";
//		}
//		}
//		
//	}

	@Override
	public int viewBookedTickets(String movieName,String theatreName) throws NoTicketBookedException{
		log.info("finding booked tickets for "+movieName);
		List<Tickets> bookedTickets= ticketRepo.findAll();
		if(bookedTickets.size()==0) {
			log.error("no ticket booked for "+movieName);
			throw new NoTicketBookedException("No ticket is booked till now");
		}
		else {
		int noOfTicketsBooked=0;
		for(Tickets ticket : bookedTickets) {
			if(ticket.getMovie().getKey().getMovieName().equals(movieName) && ticket.getMovie().getKey().getTheatreName().equals(theatreName)) {
				noOfTicketsBooked=noOfTicketsBooked+ticket.getNoOfTickets();
			}

		}
		log.info("found booked tickets");
//		List<Tickets> tic=	ticketRepo.findByKeyMovieName(movieName);
//		if(tic.size()==0) {
//			log.error("no ticket booked for "+movieName);
//			throw new NoTicketBookedException("No ticket is booked till now");
//		}
//		for(Tickets ticket : tic) {
//			
//			System.out.print(ticket.getMovie().getKey().getMovieName());
//			
//
//		}
//		 System.out.print(ticketRepo.findByKeyMovieName(movieName));
	//return "Number of tickets booked for "+movieName+" is "+noOfTicketsBooked;
		return noOfTicketsBooked;
	}
	}

	@Override
	public List<Tickets> viewviewBookedTicketsByUser(String userId) {
		// TODO Auto-generated method stub
		List<Tickets> bookedTickets= ticketRepo.findByUserid(userId);
		return bookedTickets;
	}

	@Override
	public List<Tickets> viewBookedTicketsDetails(String movieName, String theatreName) {
		// TODO Auto-generated method stub
		log.info("Finding booked tickets for " + movieName);

	    List<Tickets> bookedTickets = ticketRepo.findAll();
	    List<Tickets> matchedTickets = new ArrayList<>();

	    if (bookedTickets.isEmpty()) {
	        log.error("No ticket booked for " + movieName);
	       
	    } else {
	        for (Tickets ticket : bookedTickets) {
	            if (ticket.getMovie().getKey().getMovieName().equals(movieName) && ticket.getMovie().getKey().getTheatreName().equals(theatreName)) {
	                matchedTickets.add(ticket);
	            }
	        }
	    }
	    
	    return matchedTickets;
	}


}
