package by.teachmeskills.diplomaproject.controll;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import by.teachmeskills.diplomaproject.model.Ticket;
import by.teachmeskills.diplomaproject.service.SessionService;
import by.teachmeskills.diplomaproject.service.TicketService;
import by.teachmeskills.diplomaproject.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class TicketController {
    private final TicketService ticketService;
    private final SessionService sessionService;

    public TicketController(TicketService ticketService, SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @PostMapping("/ticket")
    public String bookingTickets(Model model, HttpSession session, HttpServletRequest request) {
        var paramMap = request.getParameterMap();
        int sessionId = Integer.parseInt(paramMap.get("sessionId")[0]);
        int row = Character.getNumericValue(paramMap.get("rowcell")[0].charAt(0));
        int cell = Character.getNumericValue(paramMap.get("rowcell")[0].charAt(1));
        int userId = Integer.parseInt(paramMap.get("userId")[0]);
        Optional<Ticket> ticket = ticketService.addTicket(new Ticket(sessionId, row, cell, userId));
        if (ticket.isEmpty()) {
            model.addAttribute("message", "Ошибка при бронировании билета");
            model.addAttribute("ticket", new Ticket());
        } else {
            model.addAttribute("message", "Вы успешно забронировали билет");
            model.addAttribute("ticket", ticket.get());
            model.addAttribute("film", sessionService.findById(sessionId));
        }
        UserUtil.checkID(model, session);
        return "result";
    }
}
