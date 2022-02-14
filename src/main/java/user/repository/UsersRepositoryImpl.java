package user.repository;

import user.model.User;
import user.services.UserService;
import user.services.UserServiceImpl;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class UsersRepositoryImpl implements UsersRepository {

    private final DataSource dataSource;
    private UserService userService;

    //language=SQL
    private static final String SQL_FIND_ALL = "select * from users where is_deleted = false";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select * from users where id = ? AND is_deleted = false";

    //language=SQL
    private static final String SQL_SAVE_USER = "insert into users (first_name, last_name, age) values (?, ?, ?)";

    //language=SQL
    private static final String SQL_UPDATE_USER = "update users set first_name = ?, last_name = ?, age = ? where id = ?";

    //language=SQL
    private static final String SQL_DELETE_USER = "update users set is_deleted = true where id = ?";

    public UsersRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.userService = new UserServiceImpl(this);
    }

    private static final Function<ResultSet, User> userMapper = resultSet -> {
        try {
            return User.builder()
                    .id(resultSet.getLong("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .age(resultSet.getInt("age"))
                    .build();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    @Override
    public void updateUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setLong(4, user.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Can't update user");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void deleteById(Long userId) {
        UserService userService = new UserServiceImpl(this);
        if (!userService.isDeleteUser(userId)) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {
                statement.setLong(1, userId);
                int affectedRows = statement.executeUpdate();
                if (affectedRows != 1) {
                    throw new SQLException("Can't delete user");
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(userMapper.apply(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void save(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Can't save user");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("Can't get id");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public User findById(Long userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return userMapper.apply(resultSet);
                } else {
                    throw new IllegalArgumentException("user not found");
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
