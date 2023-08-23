package com.moviebookingapp.models;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Tickets {
//	@DBRef
//	@Indexed
//	private Movies movie;
//	@NotNull(message="Please enter number of tickets you want to book")
//	private int noOfTickets;
//	
//	@NotNull(message="Enter your seat choice")
//	private List<String> seatNumber;

	@DBRef
	//@Indexed
	
	private Movies movie;
	@NotNull(message="Please enter number of tickets you want to book")
	private int noOfTickets;
	@NotNull(message="Please enter userid")
	private String userid;
	@NotNull(message="Enter your seat choice")
	private List<String> seatNumber;
}
