package com.example.bookMyShow.Repositories;

import com.example.bookMyShow.Models.Show;
import com.example.bookMyShow.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show , Integer> {
}

