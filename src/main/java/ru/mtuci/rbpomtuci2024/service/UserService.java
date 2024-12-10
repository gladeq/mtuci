package ru.mtuci.rbpomtuci2024.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.Repository.UserRepository;
import ru.mtuci.rbpomtuci2024.model.ApplicationUser;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ApplicationUser findOrCreateUser(String email) {
        Optional<ApplicationUser> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        ApplicationUser user = new ApplicationUser();
        user.setEmail(email);

        user.setEmail(email.substring(0, email.indexOf('@')));
        return userRepository.save(user);
    }
}