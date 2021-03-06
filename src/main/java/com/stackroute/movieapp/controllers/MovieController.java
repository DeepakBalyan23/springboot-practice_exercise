package com.stackroute.movieapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.movieapp.domain.Movie;
import com.stackroute.movieapp.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieapp.services.MovieServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class MovieController {

	private MovieServiceImpl movieService;
	
	@Autowired
	MovieController(MovieServiceImpl movieService){
		this.movieService = movieService;
	}
	
	@PostMapping("/movie")
	public ResponseEntity<?> saveMovie(@RequestBody Movie movie) {
		try {
			return new ResponseEntity<Movie> (movieService.saveMovie(movie), HttpStatus.CREATED);
		} catch (MovieAlreadyExistsException e) {
			return null;
		}
	}
	
	@GetMapping("/movies")
	public ResponseEntity<?> getAllMovies() {
		return new ResponseEntity<Iterable<Movie>> (movieService.getAllMovies(), HttpStatus.OK);
	}
	
	@GetMapping("/movie/{id}")
	public ResponseEntity<?> getMovieById(@PathVariable int id) {
		return new ResponseEntity<Optional<Movie>> (movieService.getMovieById(id), HttpStatus.OK);
	}
	
	@GetMapping("/movieByTitle/{title}")
	public ResponseEntity<?> getMovieByTitle(@PathVariable String title) {
		return new ResponseEntity<List<Movie>> (movieService.getMovieByTitle(title), HttpStatus.OK);
	}
	
	@DeleteMapping("/movie/{id}")
	public ResponseEntity<?> deleteMovieById(@PathVariable int id) {
		movieService.deleteMovie(id);
		return new ResponseEntity<String> ("Deleted", HttpStatus.OK);
	}
	
	@PutMapping("/movie/{id}")
	public ResponseEntity<?> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
		return new ResponseEntity<Movie> (movieService.updateMovie(movie, id), HttpStatus.OK);
	}
}
