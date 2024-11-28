package ru.mtuci.rbpomtuci2024.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.model.ApplicationUser;
import ru.mtuci.rbpomtuci2024.model.UserDetailsImpl;
import ru.mtuci.rbpomtuci2024.Repository.UserRepository;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserDetailsImpl.fromApplicationUser(user);
    }
}