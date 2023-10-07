package com.example.bookMyShow.Dtos.RequestDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {
    private String name;
    private Integer age;
    private String mobNo;
    private String emailId;
}
