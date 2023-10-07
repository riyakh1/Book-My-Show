package com.example.bookMyShow.Transformers;

import com.example.bookMyShow.Dtos.ResponseDtos.TicketListResponseDto;
import com.example.bookMyShow.Models.User;

public class TicketTransformer {

    public static TicketListResponseDto convertTicketEntityToResponseDto(User user){
        TicketListResponseDto ticketListResponseDto = TicketListResponseDto.builder()
                .ticketList(user.getTicketList()).build();

        return ticketListResponseDto;
    }
}
