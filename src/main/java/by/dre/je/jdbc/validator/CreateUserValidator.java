package by.dre.je.jdbc.validator;

import by.dre.je.jdbc.dao.UserDao;
import by.dre.je.jdbc.dto.CreateUserDto;
import by.dre.je.jdbc.entity.Gender;
import by.dre.je.jdbc.entity.Role;
import by.dre.je.jdbc.utils.ConnectionManager;
import by.dre.je.jdbc.utils.LocalDateFormatter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();
    private final UserDao userDao = UserDao.getInstance();

    @Override
    public ValidatorResult isValid(CreateUserDto userDto) {
        var validatorResult = new ValidatorResult();
        if (!LocalDateFormatter.isValid(userDto.getBirthday())) {
            validatorResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if (userDto.getName().isEmpty()) {
            validatorResult.add(Error.of("invalid.name", "Name is empty"));
        }
        if (userDto.getEmail().isEmpty()) {
            validatorResult.add(Error.of("invalid.email", "Email is empty"));
        }
        if (userDao.findByEmail(userDto.getEmail())) {
            validatorResult.add(Error.of("invalid.email", "Email is already exists"));
        }
        if (userDto.getPassword().isEmpty()) {
            validatorResult.add(Error.of("invalid.pwd", "Password is empty"));
        }
        if (Role.find(userDto.getRole()).isEmpty()) {
            validatorResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        if (Gender.find(userDto.getGender()).isEmpty()) {
            validatorResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        return validatorResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
