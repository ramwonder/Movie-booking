package com.moviebookingapp.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.moviebookingapp.Auth.JwtUtils;
import com.moviebookingapp.Service.MovieService;
import com.moviebookingapp.exception.MovieNotAvailableException;
import com.moviebookingapp.models.CompositeKey;
import com.moviebookingapp.models.Movies;

@SpringBootTest
class MovieControllerTest {

	@InjectMocks
	MovieController movieController;
	
	@Mock
	MovieService movieService;
	
	@Mock
	Movies movie;
	 @Mock
	    private JwtUtils jwtUtils;
	
	@BeforeEach
	void setUp() {
		movie = new Movies(new CompositeKey("RRR", "PVR"), 300);
	}

//	@Test
//	void addMovieTest() throws Exception {
////		when(movieService.addMovies(movie)).thenReturn(movie);
////		assertEquals(new ResponseEntity<>(movie,HttpStatus.CREATED), movieController.addMovies(movie));
//		   Movies movie = new Movies();
//	        // Set up other necessary data for the movie
//	        
//	        String authHeader = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJyYW1hbiIsImlhdCI6MTY4ODEwMDYwNCwiZXhwIjo2MDc3MTYyMTc2NjMyMDAwfQ.9KBPrln0VIg94O-d6cCfHvSr_G2lV6aSkdO1P0elJ7G76rgxzJhN5yR_H9liPVD-qTusDh9Xz-oc8t8E1uPxug";
//	        
//	        when(movieService.addMovies(any(Movies.class))).thenReturn(movie);
//	        doNothing().when(jwtUtils).verify(authHeader);
//	        
//	        // Act
//	        ResponseEntity<Movies> response = movieController.addMovies(movie, authHeader);
//	        
//	        // Assert
//	        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//	        assertEquals(movie, response.getBody());
//	        verify(movieService).addMovies(any(Movies.class));
//	        verify(jwtUtils).verify(authHeader);
//	
//	}
//	
	@Test
	void allMoviesTest() {
		List<Movies> movies = new ArrayList<Movies>();
		Movies newmovie = new Movies(new CompositeKey("Tiger", "PVR"), 300);
		movies.add(movie);
		movies.add(newmovie);
		when(movieService.allMovies()).thenReturn(movies);
		assertEquals(new ResponseEntity<List<Movies>>(movies,HttpStatus.OK),movieController.allMovies());
	}
	
	@Test
	void searchMoviesTest() throws MovieNotAvailableException {
		List<Movies> movies = new ArrayList<Movies>();
		Movies newmovie = new Movies(new CompositeKey("RR Raja", "PVR"), 300);
		movies.add(movie);
		movies.add(newmovie);
		when(movieService.searchByRegex("RR")).thenReturn(movies);
		assertEquals(new ResponseEntity<List<Movies>>(movies,HttpStatus.OK),movieController.searchMovies("RR"));
	}
	
	@Test
	void deleteMovieTest() throws MovieNotAvailableException {
		when(movieService.deleteMovie("RRR","PVR")).thenReturn("RRR deleted successfully!");
		assertEquals(new ResponseEntity<>("RRR deleted successfully!", HttpStatus.OK), movieController.deleteMovie("RRR","PVR"));
	}
}
