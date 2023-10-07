package com.example.bookMyShow.Models;

import com.example.bookMyShow.Enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "show_seats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String seatNo;

    private int price;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private boolean isAvailable;

    private boolean isFoodAttached;

    @ManyToOne
    @JoinColumn
    private Show show;
}
