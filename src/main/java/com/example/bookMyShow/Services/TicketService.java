package com.example.bookMyShow.Services;

import com.example.bookMyShow.Dtos.RequestDtos.TicketRequestDto;
import com.example.bookMyShow.Dtos.ResponseDtos.TicketResponseDto;
import com.example.bookMyShow.Exceptions.NoUserFoundException;
import com.example.bookMyShow.Exceptions.ShowNotFoundException;
import com.example.bookMyShow.Exceptions.TicketNotFoundException;
import com.example.bookMyShow.Models.Show;
import com.example.bookMyShow.Models.ShowSeat;
import com.example.bookMyShow.Models.Ticket;
import com.example.bookMyShow.Models.User;
import com.example.bookMyShow.Repositories.ShowRepository;
import com.example.bookMyShow.Repositories.TicketRepository;
import com.example.bookMyShow.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    UserRepository userRepository;

    public TicketResponseDto bookTicket(TicketRequestDto ticketRequestDto) throws NoUserFoundException , ShowNotFoundException , Exception{
        int userId = ticketRequestDto.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);

        //User Validation
        if(userOptional.isEmpty()){
            throw new NoUserFoundException("User id is incorrect");
        }

        int showId = ticketRequestDto.getShowId();
        Optional<Show> showOptional = showRepository.findById(showId);

        //Show Validation
        if(showOptional.isEmpty()){
            throw new ShowNotFoundException("Show Not Found");
        }

        User user = userOptional.get();
        Show show = showOptional.get();

        //Requested Seats Validation
        boolean isValid = validateReqSeatsAvailability(show , ticketRequestDto.getRequestedSeats());

        if(isValid == false){
            throw new Exception("Requested Seats are not available");
        }


        //Setting the attributes
        Ticket ticket = new Ticket();

        //1-Calculate the total price and set total price
        int totalPrice = calculateTotalPrice(show , ticketRequestDto.getRequestedSeats());
        ticket.setTotalTicketPrice(totalPrice);

        //2-Convert the list of booked Seats into String from list and set booked seats
        String bookedSeats = convertListToString(ticketRequestDto.getRequestedSeats());
        ticket.setBookedSeats(bookedSeats);


        //Setting the foreign key --> Uni-directional mapping
        ticket.setShow(show);
        ticket.setUser(user);

        //Bi-directional mapping
        ticket = ticketRepository.save(ticket);
        user.getTicketList().add(ticket);
        show.getTicketList().add(ticket);

        //Saving the parent entity
        userRepository.save(user);
        showRepository.save(show);

        return createTicketResponseDto(show , ticket);
    }

    private TicketResponseDto createTicketResponseDto(Show show, Ticket ticket) {

        TicketResponseDto ticketResponseDto = TicketResponseDto.builder()
                .showTime(show.getTime())
                .showDate(show.getDate())
                .movieName(show.getMovie().getMovieName())
                .theaterName(show.getTheater().getName())
                .bookedSeats(ticket.getBookedSeats())
                .location(show.getTheater().getLocation())
                .totalPrice(ticket.getTotalTicketPrice())
                .build();

        return ticketResponseDto;
    }

    private boolean validateReqSeatsAvailability(Show show, List<String> requestedSeats) {
        List<ShowSeat> showSeatList = show.getShowSeatList();

        boolean ans = false;
        for(ShowSeat showSeat : showSeatList){
            String seatNo = showSeat.getSeatNo();
            if(requestedSeats.contains(seatNo)){
                if(showSeat.isAvailable() == false) ans = false;
                else ans = true;
            }
        }

        return ans;
    }

    private int calculateTotalPrice(Show show, List<String> requestedSeats) {
        int totalPrice = 0;

        List<ShowSeat> showSeatList = show.getShowSeatList();

        for(ShowSeat showSeat : showSeatList){
            if(requestedSeats.contains(showSeat.getSeatNo())){
                totalPrice = totalPrice + showSeat.getPrice();
                showSeat.setAvailable(false);
            }
        }
        return totalPrice;
    }

    private String convertListToString(List<String> seats) {
        String result = "";

        for(String seatNo : seats){
            result = result + seatNo + ", ";
        }
        return result;
    }

    public void cancelTicket(int ticketId)throws TicketNotFoundException {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);

        //Validation Check
        if(ticketOptional.isEmpty()){
            throw new TicketNotFoundException("Ticket Id Is Incorrect");
        }

        Ticket ticket = ticketOptional.get();
        Show show = ticket.getShow();
        List<Ticket> ticketList = show.getTicketList();

        for(Ticket ticket1 : ticketList){
            if(ticket1.getId() == ticket.getId()) ticketList.remove(ticket1);
        }
        List<ShowSeat> showSeatList = show.getShowSeatList();
        cancelShowSeat(show , showSeatList);
    }

    private void cancelShowSeat(Show show, List<ShowSeat> showSeatList) {
        List<ShowSeat> showSeats = show.getShowSeatList();

        for(ShowSeat showSeat : showSeats){
            if(showSeatList.contains(showSeat.getSeatNo())){
                showSeat.setAvailable(true);
                showSeat.setFoodAttached(false);
            }
        }
    }
}
