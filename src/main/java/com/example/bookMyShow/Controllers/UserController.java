package com.example.bookMyShow.Controllers;

import com.example.bookMyShow.Dtos.RequestDtos.AddUserDto;
import com.example.bookMyShow.Dtos.RequestDtos.UserTicketRequestDto;
import com.example.bookMyShow.Dtos.ResponseDtos.UserResponseDto;
import com.example.bookMyShow.Models.Ticket;
import com.example.bookMyShow.Models.User;
import com.example.bookMyShow.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public String addUser(@RequestBody AddUserDto addUserDto){
        try{
            return userService.addUser(addUserDto);
        }
        catch(Exception e){
            return e.getMessage();
        }
    }
    //Get the oldest user
    @GetMapping("/getOldestUser")
    public ResponseEntity<?> getOldestUser(){
        try{
            UserResponseDto userResponseDto = userService.getOldestUser();
            return new ResponseEntity<>(userResponseDto, HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("usersGreaterThanAAge")
    public ResponseEntity<List<User>> findUsersWithGreaterAge(Integer age){
        return new ResponseEntity<>(userService.findUsersWithGreaterAge(age) , HttpStatus.OK);
    }

    //Get All ticket booked by a particular user
    @GetMapping("/ticketsBookedByAParticularUser")
    public ResponseEntity<?> ticketsBookedByAParticularUser(@RequestBody UserTicketRequestDto ticketRequestDto){
        try{
            return new ResponseEntity<>(userService.ticketsBookedByAParticularUser(ticketRequestDto) , HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }

}
