package by.teachmeskills.diplomaproject.controll;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import by.teachmeskills.diplomaproject.service.SessionService;
import by.teachmeskills.diplomaproject.util.UserUtil;

import javax.servlet.http.HttpSession;

@Controller
public class CinemaController {
    private final SessionService sessionService;

    public CinemaController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/cinema")
    public String cinema(Model model, HttpSession session) {
        UserUtil.checkID(model, session);
        model.addAttribute("sessions", sessionService.findAll());
        return "cinema";
    }
}
