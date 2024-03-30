package by.teachmeskills.diplomaproject.service;

import org.springframework.stereotype.Service;
import by.teachmeskills.diplomaproject.model.Session;
import by.teachmeskills.diplomaproject.model.Ticket;
import by.teachmeskills.diplomaproject.repository.SessionRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private static final int ROW = 6;
    private static final int CELL = 6;

    private final SessionRepository storeSession;
    private final TicketService ticketService;

    public SessionService(SessionRepository storeSession, TicketService ticketService) {
        this.storeSession = storeSession;
        this.ticketService = ticketService;
    }

    public Collection<Session> findAll() {
        return storeSession.findAll();
    }

    public Optional<Session> findById(int id) {
        return storeSession.findById(id);
    }

    public boolean[][] orderedTickets(int id) {
        boolean[][] result = new boolean[ROW][CELL];
        List<Ticket> ticketList = (List<Ticket>) ticketService.findTickets(id);
        for (Ticket ticket : ticketList) {
            int row = ticket.getRow();
            int cell = ticket.getCell();
            result[row][cell] = true;
        }
        return result;
    }
}
