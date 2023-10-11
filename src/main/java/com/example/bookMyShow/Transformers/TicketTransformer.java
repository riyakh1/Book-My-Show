package com.example.bookMyShow.Transformers;

import com.example.bookMyShow.Dtos.ResponseDtos.TicketListResponseDto;
import com.example.bookMyShow.Dtos.extraDto.TicketListDto;
import com.example.bookMyShow.Models.Ticket;
import com.example.bookMyShow.Models.User;

import java.util.ArrayList;
import java.util.List;

public class TicketTransformer {

    public static TicketListResponseDto convertTicketEntityToResponseDto(User user){
        List<TicketListDto> ticketDtoList = new ArrayList<>();
        for(Ticket t :user.getTicketList()){
            ticketDtoList.add(new TicketListDto(t.getTotalTicketPrice(), t.getBookedSeats(), t.getBookedAt()));
        }

            TicketListResponseDto ticketListResponseDto = new TicketListResponseDto(ticketDtoList);


        return ticketListResponseDto;
    }
}
