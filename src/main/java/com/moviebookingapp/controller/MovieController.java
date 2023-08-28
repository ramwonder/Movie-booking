package com.moviebookingapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.Service.MovieService;
import com.moviebookingapp.exception.MovieNotAvailableException;
import com.moviebookingapp.models.Movies;
//import com.moviebookingapp.kafka.Producer;

import lombok.extern.slf4j.Slf4j;
import com.moviebookingapp.Auth.JwtUtils;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://moviebookingapp23.z29.web.core.windows.net"
    })
@Slf4j
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class MovieController {
	@Autowired
	private MovieService movieService;
//	@Autowired
//	Producer producer;

	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/add")
	//@RequestHeader(value="authorization",defaultValue = "")String auth
	public ResponseEntity<Movies> addMovies(@Valid @RequestBody Movies movie) throws Exception{
		log.info("Insertion of movie is in progress");
		//producer.sendMessage(movie.getKey().getMovieName());
		//jwtUtils.verify(auth);
		
		Movies newMovie = movieService.addMovies(movie);
		log.info("Movie inserted successfully");
		return new ResponseEntity<>(newMovie,HttpStatus.CREATED);
		
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Movies>> allMovies(){
		log.info("List of all mmovies is fetching");
		List<Movies> movies = movieService.allMovies();
		log.info("List of movies are fetched");
		return new ResponseEntity<List<Movies>>(movies,HttpStatus.OK);
	}
	
	@GetMapping("/movies/search/{movieName}")
	public ResponseEntity<List<Movies>> searchMovies(@PathVariable String movieName) throws MovieNotAvailableException{
		log.info("Movies are being searched by name");
		List<Movies> movies = movieService.searchByRegex(movieName);
		log.info("Movie are displayed");
		return new ResponseEntity<List<Movies>>(movies,HttpStatus.OK);
	}
	
	@DeleteMapping("/{movieName}/{theatreName}/delete")
	public ResponseEntity<?> deleteMovie(@PathVariable String movieName, @PathVariable String theatreName) throws MovieNotAvailableException{
		log.info("Delete movie by name is in progress");
		String msg=movieService.deleteMovie(movieName,theatreName);
		log.info("Movie deleted successfully");
		return new ResponseEntity<String>(msg,HttpStatus.OK);
		
	}

}
