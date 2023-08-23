package com.moviebookingapp.models;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="movies")
public class Movies {
	
	@Id
	//@Indexed
	private CompositeKey key;
	
	@NotNull(message="Please enter total number of tickets available")
	private int totalNoOfTickets;


}
