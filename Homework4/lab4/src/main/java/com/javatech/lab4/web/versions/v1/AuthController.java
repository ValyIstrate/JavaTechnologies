package com.javatech.lab4.web.versions.v1;

import com.javatech.lab4.services.AuthService;
import com.javatech.lab4.web.requests.ActivateAccountRq;
import com.javatech.lab4.web.requests.LoginRq;
import com.javatech.lab4.web.requests.RequestActivationRq;
import com.javatech.lab4.web.responses.TokenRs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth Controller")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/request-activation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Request Activation",
            tags = {"Auth"},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<Void> requestActivation(@RequestBody RequestActivationRq request) {
        authService.requestActivation(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/activate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Activate",
            tags = {"Auth"},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<Void> activateAccount(@RequestBody ActivateAccountRq request) {
        authService.activateAccount(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Login",
            tags = {"Auth"},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<TokenRs> activateAccount(@RequestBody LoginRq request) {
        return new ResponseEntity<>(
                authService.login(request),
                HttpStatus.OK
        );
    }
}
