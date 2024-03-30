package by.teachmeskills.diplomaproject.util;

import org.springframework.ui.Model;
import by.teachmeskills.diplomaproject.model.User;


import javax.servlet.http.HttpSession;

public class UserUtil {

    private UserUtil() {
    }

    public static void checkID(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}
