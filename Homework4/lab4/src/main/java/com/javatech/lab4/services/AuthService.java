package com.javatech.lab4.services;

import com.javatech.lab4.persistence.entities.AppUser;
import com.javatech.lab4.persistence.entities.OneTimePassword;
import com.javatech.lab4.persistence.repositories.AppUserRepository;
import com.javatech.lab4.persistence.repositories.InstructorRepository;
import com.javatech.lab4.persistence.repositories.OneTimePasswordRepository;
import com.javatech.lab4.persistence.repositories.StudentRepository;
import com.javatech.lab4.services.exceptions.BadRequestException;
import com.javatech.lab4.services.exceptions.EntityNotFoundException;
import com.javatech.lab4.services.exceptions.WrongOtpException;
import com.javatech.lab4.services.utils.MailDetails;
import com.javatech.lab4.web.requests.ActivateAccountRq;
import com.javatech.lab4.web.requests.LoginRq;
import com.javatech.lab4.web.requests.RequestActivationRq;
import com.javatech.lab4.web.requests.TokenRs;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final OneTimePasswordRepository oneTimePasswordRepository;
    private final AppUserRepository appUserRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${otp_expiration_value}")
    private Long OTP_EXPIRATION_VALUE;

    private static final Integer LENGTH = 6;

    private final Random randomGenerator = new Random();

    public void requestActivation(RequestActivationRq request) {
        var appUser = appUserRepository.findByUserEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("Hidden message"));

        if (Boolean.TRUE.equals(appUser.isEnabled())) {
            return;
        }

        String userName = getUserFirstName(request.email(), appUser);

        var newOtp = createOtpForUser(request.email());
        sendActivationMail(userName, newOtp, request.email());
    }

    public void activateAccount(ActivateAccountRq request) {
        verifyActivationRequest(request);

        var currentOtp = oneTimePasswordRepository.findByUserEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("Hidden message"));

        if (!currentOtp.getOtpCode().equals(request.otp())) {
            throw new WrongOtpException();
        }

        var currentUser = appUserRepository.findByUserEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("Hidden message"));

        handleOtpVerification(currentOtp, currentUser);

        currentUser.setPassword(passwordEncoder.encode(request.password()));
        currentUser.setIsActivated(true);
        appUserRepository.save(currentUser);
    }

    private void handleOtpVerification(OneTimePassword otp, AppUser appUser) {
        if(Date.from(Instant.now()).after(otp.getExpirationDate())) {
            String email = otp.getUserEmail();

            oneTimePasswordRepository.delete(otp);
            var newOtp = createOtpForUser(otp.getUserEmail());

            sendActivationMail(getUserFirstName(email, appUser), newOtp, email);
            throw new BadRequestException("Expired OTP. A new one is created and will be sent to you by mail");
        }

        oneTimePasswordRepository.delete(otp);
    }

    private String getUserFirstName(String email, AppUser appUser) {
        String userName;
        if (appUser.getRole().equalsIgnoreCase("student")) {
            userName = studentRepository.findByEmail(email).getName().split(" ")[0];
        } else if (appUser.getRole().equalsIgnoreCase("instructor")) {
            userName = instructorRepository.findByEmail(email).getName().split(" ")[0];
        } else {
            // ADMIN
            userName = "Admin";
        }
        return userName;
    }

    private String createOtpForUser(String userEmail) {
        deleteOldOtp(userEmail);

        return oneTimePasswordRepository.save(
                OneTimePassword.builder()
                        .otpCode(createOtpCode().get())
                        .userEmail(userEmail)
                        .expirationDate(new Date(System.currentTimeMillis() + OTP_EXPIRATION_VALUE))
                        .build()
        ).getOtpCode();
    }

    private void deleteOldOtp(String userEmail) {
        oneTimePasswordRepository.findByUserEmail(userEmail).ifPresent(oneTimePasswordRepository::delete);
    }

    private Supplier<String> createOtpCode() {
        return () -> {
            StringBuilder oneTimePassword = new StringBuilder();
            int i = LENGTH;

            while (i != 0) {
                int charOrNum = randomGenerator.nextInt(2);
                if(charOrNum == 0) {
                    char c = (char)(randomGenerator.nextInt(26) + 'A');
                    oneTimePassword.append(c);
                } else {
                    int num = randomGenerator.nextInt(10);
                    oneTimePassword.append(num);
                }
                i--;
            }

            return oneTimePassword.toString();
        };
    }

    private void sendActivationMail(String name, String otp, String email) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", name);
        properties.put("otp_code", otp);

        MailDetails mail =  MailDetails.builder()
                .to(email)
                .htmlTemplate(new MailDetails.HtmlTemplate("ActivateAccount", properties))
                .subject("Activate your account!")
                .build();

        sendCompletableFutureMail(mail);
    }

    private void sendCompletableFutureMail(MailDetails mail) {
        CompletableFuture.runAsync(() -> {
            try {
                emailSenderService.sendEmail(mail);
            } catch (MessagingException ex) {
                throw new CompletionException(ex);
            }
        }).exceptionally(e -> {
            log.warn("Email could not be sent. Reasons: " + e.getMessage());
            return null;
        });
    }

    private void verifyActivationRequest(ActivateAccountRq dto) {
        if(dto.email() == null || dto.email().isBlank()) {
            throw new BadRequestException("Specialist Id can not be null");
        }

        if(dto.otp() == null || dto.otp().isBlank()) {
            throw new BadRequestException("One Time Password can not be null");
        }

        if (dto.password() == null || dto.password().isBlank()) {
            throw new BadRequestException("Password can not be null");
        }
    }

    public TokenRs login(LoginRq request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        AppUser appUser = appUserRepository.findByUserEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("Message Hidden"));

        return new TokenRs(jwtService.generateToken(appUser));
    }
}
