package mtuci.rbpomtuci2024.controller;


import lombok.RequiredArgsConstructor;
import mtuci.rbpomtuci2024.model.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mtuci.rbpomtuci2024.configuration.JwtTokenProvider;
import mtuci.rbpomtuci2024.model.ApplicationUser;
import mtuci.rbpomtuci2024.model.AuthenticationRequest;
import mtuci.rbpomtuci2024.model.AuthenticationResponse;
import mtuci.rbpomtuci2024.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import mtuci.rbpomtuci2024.service.impl.UserDetailsServiceImpl;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsServiceImpl UserDetailsServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody ApplicationUser user) {
        try {
            ApplicationUser registeredUser = UserDetailsServiceImpl.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            String email = request.getEmail();

            ApplicationUser user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));

            String token = jwtTokenProvider.createToken(email, user.getRole().getGrantedAuthorities());

            return ResponseEntity.ok(new AuthenticationResponse(email, token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
}
