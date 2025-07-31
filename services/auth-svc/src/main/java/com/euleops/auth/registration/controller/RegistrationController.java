package com.euleops.auth.registration.controller;

import com.euleops.auth.registration.dto.AuthResponse;
import com.euleops.auth.registration.dto.RegisterRequest;
import com.euleops.auth.registration.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Public entry-point for user self-registration.
 *
 *  • POST /api/v1/registration → crea el usuario, genera JWT y dispara el e-mail de activación.
 *  • La especificación OpenAPI de este endpoint vive en src/main/resources/openapi/registration-api.yaml
 */
@RestController
@RequestMapping("/auth/registration")
@RequiredArgsConstructor
@Validated
public class RegistrationController {

    private final RegisterService registerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return registerService.register(request);
    }
}
