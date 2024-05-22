package by.dre.je.jdbc.dto;

import by.dre.je.jdbc.entity.Gender;
import by.dre.je.jdbc.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Integer id;
    String name;
    LocalDate birthday;
    String email;
    String password;
    Role role;
    Gender gender;
}
