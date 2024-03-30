package by.teachmeskills.diplomaproject.controll;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import by.teachmeskills.diplomaproject.model.User;
import by.teachmeskills.diplomaproject.service.UserService;
import by.teachmeskills.diplomaproject.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationPage(Model model, HttpSession session) {
        UserUtil.checkID(model, session);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user, HttpSession session) {
        Optional<User> regUser = userService.addUser(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой/телефоном уже существует");
        } else {
            model.addAttribute("message", "Регистрация прошла успешно");
        }
        UserUtil.checkID(model, session);
        return "registration";
    }

    @GetMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail,
                            HttpSession session) {
        UserUtil.checkID(model, session);
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> userDb = userService.authorization(
                user.getEmail(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/login?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/cinema";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
