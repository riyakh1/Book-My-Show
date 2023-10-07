package com.example.bookMyShow.Dtos.ResponseDtos;

import com.example.bookMyShow.Models.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketListResponseDto {

    private List<Ticket> ticketList;
}
