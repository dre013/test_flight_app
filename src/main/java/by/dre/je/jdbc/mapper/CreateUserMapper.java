package by.dre.je.jdbc.mapper;

import by.dre.je.jdbc.dto.CreateUserDto;
import by.dre.je.jdbc.entity.Gender;
import by.dre.je.jdbc.entity.Role;
import by.dre.je.jdbc.entity.User;
import by.dre.je.jdbc.utils.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<User, CreateUserDto> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .name(createUserDto.getName())
                .birthday(LocalDateFormatter.format(createUserDto.getBirthday()))
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .role(Role.valueOf(createUserDto.getRole()))
                .gender(Gender.valueOf(createUserDto.getGender()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
