package by.teachmeskills.diplomaproject.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import by.teachmeskills.diplomaproject.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class SessionRepository {
    private final BasicDataSource dataSource;

    public SessionRepository(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Collection<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM sessions ORDER BY id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    sessions.add(new Session(
                            it.getInt("id"),
                            it.getString("name")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessions;
    }

    public Optional<Session> findById(int id) {
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM sessions WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(new Session(
                            it.getInt("id"),
                            it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
