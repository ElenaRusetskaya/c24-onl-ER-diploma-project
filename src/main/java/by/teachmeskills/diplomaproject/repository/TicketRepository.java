package by.teachmeskills.diplomaproject.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import by.teachmeskills.diplomaproject.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepository {
    private final BasicDataSource dataSource;

    public TicketRepository(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO ticket (session_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    ticket.setId(it.getInt("id"));
                }
            }
            return Optional.of(ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Collection<Ticket> findTickets(int sessionId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM ticket WHERE session_id = ? ORDER BY id")) {
            ps.setInt(1, sessionId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(
                            it.getInt("id"),
                            it.getInt("session_id"),
                            it.getInt("pos_row"),
                            it.getInt("cell"),
                            it.getInt("user_id")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }
}