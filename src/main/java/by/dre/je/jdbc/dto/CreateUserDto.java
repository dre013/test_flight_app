package by.dre.je.jdbc.dto;

import by.dre.je.jdbc.entity.Gender;
import by.dre.je.jdbc.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CreateUserDto {
    String name;
    String birthday;
    String email;
    String password;
    String role;
    String gender;
}
