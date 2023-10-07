package com.example.bookMyShow.Controllers;

import com.example.bookMyShow.Dtos.RequestDtos.TicketRequestDto;
import com.example.bookMyShow.Dtos.ResponseDtos.TicketResponseDto;
import com.example.bookMyShow.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping("bookTicket")
    public ResponseEntity<?> bookTicket(@RequestBody TicketRequestDto ticketRequestDto){
        try{
            return new ResponseEntity<>(ticketService.bookTicket(ticketRequestDto) , HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cancelTicket")
    public ResponseEntity<String> cancelTicket(@RequestParam int ticketId){
        try{
            ticketService.cancelTicket(ticketId);
            return new ResponseEntity<>("Ticket has been cancelled successfully" , HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }
}
