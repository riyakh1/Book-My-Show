package com.example.bookMyShow.Controllers;

import com.example.bookMyShow.Dtos.RequestDtos.TheaterEntryDto;
import com.example.bookMyShow.Dtos.RequestDtos.TheaterSeatsEntryDto;
import com.example.bookMyShow.Services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theater")
public class TheaterController {

    @Autowired
    TheaterService theaterService;

    @PostMapping("/add")
    public String addTheater(@RequestBody TheaterEntryDto theaterEntryDto){
        return theaterService.addTheater(theaterEntryDto);
    }

    @PostMapping("/addTheaterSeats")
    public String addTheaterSeats(@RequestBody TheaterSeatsEntryDto theaterSeatsEntryDto){
        return theaterService.addTheaterSeats(theaterSeatsEntryDto);
    }

    //Get count of unique locations of a theater
    @GetMapping("/countOfUniqueLocationsOfATheater")
    public int countOfUniqueLocationsOfATheater(){
        return theaterService.countOfUniqueLocationsOfATheater();
    }
}
