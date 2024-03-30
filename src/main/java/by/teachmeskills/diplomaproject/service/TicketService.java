package by.teachmeskills.diplomaproject.service;

import org.springframework.stereotype.Service;
import by.teachmeskills.diplomaproject.model.Ticket;
import by.teachmeskills.diplomaproject.repository.TicketRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository storeTicket;

    public TicketService(TicketRepository storeTicket) {
        this.storeTicket = storeTicket;
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        return storeTicket.addTicket(ticket);
    }

    public Collection<Ticket> findTickets(int sessionId) {
        return storeTicket.findTickets(sessionId);
    }
}