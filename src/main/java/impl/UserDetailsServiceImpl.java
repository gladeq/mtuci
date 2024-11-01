package impl;

import Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import model.ApplicationRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import model.ApplicationUser;
import model.UserDetailsImpl;
import Repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserDetailsImpl.fromApplicationUser(user);
    }

    public ApplicationUser registerUser(ApplicationUser user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists"); // Или кастомное исключение
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ApplicationRole userRole = RoleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role 'USER' not found. Please create it."));
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }
}
