package com.moviebookingapp.Service;

import java.util.List;

import com.moviebookingapp.exception.MovieNotAvailableException;
import com.moviebookingapp.exception.NoTicketBookedException;
import com.moviebookingapp.exception.UserNotExistException;
import com.moviebookingapp.models.Tickets;

public interface TicketService {
	public String bookTickets(Tickets tickets) throws MovieNotAvailableException, UserNotExistException;
	//public String ticketStatus(String movieName);
	public int viewBookedTickets(String movieName,String theatreName) throws NoTicketBookedException;

	public List<Tickets> viewBookedTicketsDetails(String movieName,String theatreName);

	public List<Tickets> viewviewBookedTicketsByUser(String userId);
	

}
