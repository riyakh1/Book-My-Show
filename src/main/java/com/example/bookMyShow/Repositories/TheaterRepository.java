package com.example.bookMyShow.Repositories;

import com.example.bookMyShow.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {

    Theater findByLocation(String location);
}
