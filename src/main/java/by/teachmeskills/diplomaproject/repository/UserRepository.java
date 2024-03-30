package by.teachmeskills.diplomaproject.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import by.teachmeskills.diplomaproject.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class UserRepository {
    private final BasicDataSource dataSource;

    public UserRepository(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<User> addUser(User user) {
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users (username, email, phone, password) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    user.setId(it.getInt("id"));
                }
            }
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> authorization(String email, String password) {
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    return Optional.of(new User(
                            it.getInt("id"),
                            it.getString("username"),
                            it.getString("email"),
                            it.getString("phone")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
