package com.example.bookMyShow.Repositories;

import com.example.bookMyShow.Models.TheaterSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterSeatRepository extends JpaRepository<TheaterSeat , Integer> {
}
