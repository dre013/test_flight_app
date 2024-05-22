package by.dre.je.jdbc.service;

import by.dre.je.jdbc.dao.UserDao;
import by.dre.je.jdbc.dto.CreateUserDto;
import by.dre.je.jdbc.dto.UserDto;
import by.dre.je.jdbc.exception.ValidationException;
import by.dre.je.jdbc.mapper.CreateUserMapper;
import by.dre.je.jdbc.mapper.UserMapper;
import by.dre.je.jdbc.validator.CreateUserValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private final static UserService INSTANCE = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();


    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password).map(userMapper::mapFrom);
    }

    public Integer create(CreateUserDto createUserDto) {
        var validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var user = createUserMapper.mapFrom(createUserDto);
        userDao.save(user);
        return user.getId();
    }
    public static UserService getInstance() {
        return INSTANCE;
    }
}
