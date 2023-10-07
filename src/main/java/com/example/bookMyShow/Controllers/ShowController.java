package com.example.bookMyShow.Controllers;

import com.example.bookMyShow.Dtos.RequestDtos.AddShowDto;
import com.example.bookMyShow.Dtos.RequestDtos.ShowSeatsDto;
import com.example.bookMyShow.Dtos.RequestDtos.ShowTimeDto;
import com.example.bookMyShow.Dtos.ResponseDtos.ShowTimeResponseDto;
import com.example.bookMyShow.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    ShowService showService;

    @PostMapping("/add")
    public String addShow(@RequestBody AddShowDto addShowDto){
        try{
            return showService.addShow(addShowDto);
        }
        catch(Exception e){
            return e.getMessage();
        }
    }

    @PostMapping("associateSeats")
    public String associateShowSeats(@RequestBody ShowSeatsDto showSeatsDto){
        try{
            return showService.associateShowSeats(showSeatsDto);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    //Get Show time for a theater and a movie
    @GetMapping("/showTimeOfAMovieInATheater")
    public ResponseEntity<ShowTimeResponseDto> showTimeOfAMovieInATheater(@RequestBody ShowTimeDto showTimeDto){
        try {
            ShowTimeResponseDto timeResponseDto = showService.showTimeOfAMovieInATheater(showTimeDto);
            timeResponseDto.setStatusMessage("SUCCESS");
            timeResponseDto.setStatusCode(200);
            return new ResponseEntity<>(timeResponseDto , HttpStatus.OK);
        }
        catch (Exception e){
            ShowTimeResponseDto timeResponseDto = new ShowTimeResponseDto();
            timeResponseDto.setStatusMessage(e.getMessage());
            timeResponseDto.setStatusCode(400);

            return new ResponseEntity<>(timeResponseDto , HttpStatus.BAD_REQUEST);
        }
    }
}
