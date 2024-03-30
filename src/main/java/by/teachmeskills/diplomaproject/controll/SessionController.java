package by.teachmeskills.diplomaproject.controll;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import by.teachmeskills.diplomaproject.model.Session;
import by.teachmeskills.diplomaproject.service.SessionService;
import by.teachmeskills.diplomaproject.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class SessionController {
    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }


    @PostMapping("/session")
    public String sessionId(HttpServletRequest req, Model model, HttpSession session) {
        int id = Integer.parseInt(req.getParameter("id"));
        Optional<Session> sessionWithName = service.findById(id);
        if (sessionWithName.isEmpty()) {
            model.addAttribute("filmSession", new Session());
            model.addAttribute("message", "Ошибка программы при получении информации о фильме");
        } else {
            model.addAttribute("filmSession", sessionWithName.get());
        }
        model.addAttribute("orderedTicketsArray", service.orderedTickets(id));
        UserUtil.checkID(model, session);
        return "ticket";
    }
}
