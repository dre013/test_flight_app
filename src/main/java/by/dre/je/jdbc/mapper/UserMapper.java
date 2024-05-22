package by.dre.je.jdbc.mapper;

import by.dre.je.jdbc.dto.UserDto;
import by.dre.je.jdbc.entity.Gender;
import by.dre.je.jdbc.entity.Role;
import by.dre.je.jdbc.entity.User;
import by.dre.je.jdbc.utils.LocalDateFormatter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<UserDto, User>{
    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .name(user.getName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .gender(user.getGender())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
