package com.example.bookMyShow.Controllers;

import com.example.bookMyShow.Dtos.RequestDtos.AddMovieDto;
import com.example.bookMyShow.Dtos.RequestDtos.TotalMovieRevenueDto;
import com.example.bookMyShow.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    MovieService movieService;

    @PostMapping("/add")
    public String addMovie(@RequestBody AddMovieDto addMovieDto){
        return movieService.addMovie(addMovieDto);
    }

    //Find the movieName with the Maximum number of Shows across all places combined.
    @GetMapping("/`movieWithTheMaximumNumberOfShows`")
    public ResponseEntity<String> movieWithTheMaximumNumberOfShows(){
        return new ResponseEntity<>(movieService.movieWithTheMaximumNumberOfShows() , HttpStatus.OK);
    }

    //Find total collection (revenue) that a particular movie has made in its lifetime (across all theaters combined)
    @GetMapping("/totalRevenueOfAParticularMovie")
    public ResponseEntity<?> totalRevenueOfAParticularMovie(@RequestParam int movieId){
        try{
            return new ResponseEntity<>(movieService.totalRevenueOfAParticularMovie(movieId) , HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    //Rate movie Flop or Hit based
    @GetMapping("/movieHitOrFlop")
    public ResponseEntity<String> movieHitOrFlop(@RequestBody TotalMovieRevenueDto movieRevenueDto){
        try{
            return new ResponseEntity<>(movieService.movieHitOrFlop(movieRevenueDto) , HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }
}
