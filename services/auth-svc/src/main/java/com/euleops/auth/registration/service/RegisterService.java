package com.euleops.auth.registration.service;

import com.euleops.auth.client.NotificationFeignClient;
import com.euleops.auth.config.JwtUtils;
import com.euleops.auth.constants.Roles;
import com.euleops.auth.exception.UserAlreadyExistsException;
import com.euleops.auth.model.User;
import com.euleops.auth.registration.dto.AuthResponse;
import com.euleops.auth.registration.dto.RegisterRequest;
import com.euleops.auth.repository.UserRepository;
import com.euleops.notification.client.model.Channel;
import com.euleops.notification.client.model.SendNotificationRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final JwtUtils jwt;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final NotificationFeignClient notificationsApi;

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByName(req.getUsername())) {
            throw new UserAlreadyExistsException(req.getUsername());
        }

        User user = User.builder()
                .name(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Roles.CUSTOMER)
                .isEnabled(true)
                .keycloakId(UUID.randomUUID())
                .build();
        userRepository.save(user);

        System.out.println("Registered user: " + user.getName());
        String payload = """
    {
      "event": "user-registered",
      "username": "%s",
      "email": "%s"
    }
    """.formatted(user.getName(), user.getEmail());

        kafkaTemplate.send("user-registered", payload);


        UserDetails ud = userDetailsService.loadUserByUsername(user.getEmail());

        String token = jwt.generateToken(ud);

        notificationsApi.sendEmail(
                new SendNotificationRequest()
                        .to(req.getEmail())
                        .subject("Activate your account")
                        .content(buildHtml(user, token))
                        .channel(Channel.EMAIL)
        );

        return new AuthResponse(token, "Bearer");
    }


    private String buildHtml(User user, String token) {
        return """
               <html>
                 <body>
                   <p>Hello %s,</p>
                   <p>Thanks for registering! Click the link below to activate your account:</p>
                   <p><a href="https://app.euleops.com/activate?token=%s">Activate</a></p>
                 </body>
               </html>
               """.formatted(user.getName(), token);
    }
}
