package com.moviebookingapp.serviceImpl;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//
//import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import static org.junit.matchers.JUnitMatchers.*;

import com.moviebookingapp.Repository.MoviesRepo;
import com.moviebookingapp.Repository.TicketRepo;
import com.moviebookingapp.Repository.UserRepo;
import com.moviebookingapp.exception.MovieNotAvailableException;
import com.moviebookingapp.exception.NoTicketBookedException;
import com.moviebookingapp.exception.UserNotExistException;
import com.moviebookingapp.models.CompositeKey;
import com.moviebookingapp.models.Movies;
import com.moviebookingapp.models.Tickets;
import com.moviebookingapp.models.User;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

	@InjectMocks
	TicketServiceImpl ticketService;
	
	@Mock
	TicketRepo ticketRepo;
	@Mock
	MoviesRepo movieRepo;

	 @Mock
	   private UserRepo userRepo;
	@Mock
	Tickets ticket;
	@Mock
	Movies movie;
	
	@BeforeEach
	void setUp() {
		ticket = new Tickets(new Movies(new CompositeKey("RRR", "PVR"), 300),2,"mano23",Arrays.asList("D1","D2"));
		movie = new Movies(new CompositeKey("RRR", "PVR"), 300);
	}
	 @Test
	    public void testBookTickets() throws MovieNotAvailableException, UserNotExistException {
	        // Arrange
	       // Tickets tickets = new Tickets();
	        // Set up other necessary data for the tickets
	        
		  String movieName = ticket.getMovie().getKey().getMovieName();
	        String theatreName = ticket.getMovie().getKey().getTheatreName();
	        
	        User user = new User();
	        user.setLoginId(ticket.getUserid());
	        
	        Movies movie = new Movies();
	        movie.setTotalNoOfTickets(10); // Assuming an initial ticket count
	        
	        when(userRepo.existsById(ticket.getUserid())).thenReturn(true);
	        when(movieRepo.findBymovieName(movieName, theatreName)).thenReturn(movie);
	        when(ticketRepo.save(ticket)).thenReturn(ticket);
	        
	        // Act
	        String result = ticketService.bookTickets(ticket);
	        
	        // Assert
	        assertEquals("Ticket Booked", result);
	        assertEquals(10 - ticket.getNoOfTickets(), movie.getTotalNoOfTickets());
	        verify(userRepo).existsById(ticket.getUserid());
	        verify(movieRepo).findBymovieName(movieName, theatreName);
	        verify(movieRepo).save(movie);
	        verify(ticketRepo).save(ticket);
	    }
	 
	 @Test
	 void testBookTickets_MovieNotAvailableException() {
	     // Arrange
	     // Set up the tickets object as needed for the test case
	     
	     // Mock the behavior of the userRepo
	     when(userRepo.existsById(Mockito.anyString())).thenReturn(true);
	     
	     // Mock the behavior of the moviesRepo to return null
	     when(movieRepo.findBymovieName(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
	     
	     // Act & Assert
	     assertThrows(MovieNotAvailableException.class, () -> ticketService.bookTickets(ticket));
	 }

@Test
void testBookTickets_UserNotExistException() {
    // Arrange
    
    // Set up the tickets object as needed for the test case
    
    // Mock the behavior of the userRepo to return false
    Mockito.when(userRepo.existsById(Mockito.anyString())).thenReturn(false);
    
    // Act & Assert
    assertThrows(UserNotExistException.class, () -> ticketService.bookTickets(ticket));
}
	
	@Test
	void viewBookedTicketsExceptionTest() throws NoTicketBookedException{
		List<Tickets> tickets = new ArrayList<>();
		when(ticketRepo.findAll()).thenReturn(tickets);
		assertThrows(NoTicketBookedException.class, ()->{
			ticketService.viewBookedTickets("jhs","ppk");
		});
	}
	
	@Test
	void viewBookedTicketsTest() throws NoTicketBookedException{
		List<Tickets> tickets = new ArrayList<>();
		tickets.add(ticket);
		when(ticketRepo.findAll()).thenReturn(tickets);
		assertEquals(ticket.getNoOfTickets(), 
				ticketService.viewBookedTickets("RRR","PVR"));
	}
	
}
