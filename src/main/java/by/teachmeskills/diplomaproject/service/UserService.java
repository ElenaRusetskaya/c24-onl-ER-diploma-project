package by.teachmeskills.diplomaproject.service;

import org.springframework.stereotype.Service;
import by.teachmeskills.diplomaproject.model.User;
import by.teachmeskills.diplomaproject.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository storeUser;

    public UserService(UserRepository userRepository) {
        this.storeUser = userRepository;
    }

    public Optional<User> addUser(User user) {
        return storeUser.addUser(user);
    }

    public Optional<User> authorization(String email, String password) {
        return storeUser.authorization(email, password);
    }
}
