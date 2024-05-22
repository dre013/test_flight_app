package by.dre.je.jdbc.dao;

import by.dre.je.jdbc.entity.Gender;
import by.dre.je.jdbc.entity.Role;
import by.dre.je.jdbc.entity.User;
import by.dre.je.jdbc.exception.DaoException;
import by.dre.je.jdbc.utils.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO users (name, birthday, email, password, role, gender)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT * FROM users
            WHERE email = ? AND password = ?
            """;

    private static final String FIND_BY_EMAIL = """
            SELECT * FROM users
            WHERE email = ?
            """;

    @SneakyThrows
    public boolean findByEmail(String email) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_EMAIL)) {
            statement.setString(1, email);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            statement.setString(1, email);
            statement.setString(2, password);
            var resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()){
                user = buildEntity(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }



    private User buildEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getTimestamp("birthday").toLocalDateTime().toLocalDate())
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .role(Role.valueOf(resultSet.getString("role")))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .build();
    }


    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    @SneakyThrows
    public User save(User user) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setDate(2, Date.valueOf(user.getBirthday()));
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().name());
            statement.setString(6, user.getGender().name());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();

            if (keys.next())
                user.setId(keys.getInt("id"));

            return user;
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
