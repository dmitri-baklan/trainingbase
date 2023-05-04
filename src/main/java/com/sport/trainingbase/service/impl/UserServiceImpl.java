package com.sport.trainingbase.service.impl;

import com.sport.trainingbase.domain.dto.request.EmailOTPDTORequest;
import com.sport.trainingbase.domain.dto.request.UserDTORequest;
import com.sport.trainingbase.domain.dto.response.EmailOTPDTOResponse;
import com.sport.trainingbase.domain.dto.response.OTPResponseStatus;
import com.sport.trainingbase.domain.dto.response.UserDTOResponse;
import com.sport.trainingbase.domain.model.user.EmailOTP;
import com.sport.trainingbase.domain.model.user.User;
import com.sport.trainingbase.domain.model.user.UserPrincipal;
import com.sport.trainingbase.exception.EmailExistException;
import com.sport.trainingbase.exception.EmailNotFoundException;
import com.sport.trainingbase.repository.EmailOTPRepository;
import com.sport.trainingbase.repository.UserRepository;
import com.sport.trainingbase.service.EmailSender;
import com.sport.trainingbase.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static com.sport.trainingbase.EmailConstants.OTP_EXPIRATION_MINUTES;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Resource
    private EmailSender emailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailOTPRepository emailOTPRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error(String.format("User not found with email: %s", email));
                    return new UsernameNotFoundException(String.format("User not found with email: %s", email));
                });
        log.info(String.format("User found with email: %s", email));
        return new UserPrincipal(user);
    }


    @Override
    public String saveEmailAndSendOTP(String email) throws EmailExistException {
        validateEmailUniqueness(email);
        Integer otp = generateOTP();
        Optional<EmailOTP> emailOTPOptional = emailOTPRepository.findEmailOTPByEmail(email);
        if (emailOTPOptional.isPresent()) {
            emailOTPOptional.get().setOtp(otp);
            emailOTPOptional.get().setCreationTime(LocalDateTime.now());
        } else {
            emailOTPRepository.save(EmailOTP.builder()
                    .email(email)
                    .otp(otp)
                    .isVerified(false)
                    .creationTime(LocalDateTime.now())
                    .build());
        }
        return email;
    }

    @Override
    public EmailOTPDTOResponse verifyEmailWithOTP(EmailOTPDTORequest otpDtoRequest) throws EmailNotFoundException {
        String message = "Email: " + otpDtoRequest.getEmail() + "is not found for verifying";
        EmailOTP emailOTP = emailOTPRepository.findEmailOTPByEmail(otpDtoRequest.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(message));
        EmailOTPDTOResponse otpDtoResponse = EmailOTPDTOResponse.builder()
                .email(otpDtoRequest.getEmail())
                .otp(otpDtoRequest.getOtp())
                .build();
        if (isOTPExpired(emailOTP)) {
            otpDtoResponse.setOtpResponseStatus(OTPResponseStatus.EXPIRED);
        } else {
            if (Objects.equals(otpDtoRequest.getOtp(), emailOTP.getOtp())) {
                otpDtoResponse.setOtpResponseStatus(OTPResponseStatus.SUCCESS);
                emailOTP.setVerified(true);
            } else {
                otpDtoResponse.setOtpResponseStatus(OTPResponseStatus.FAILED);
                emailOTP.setVerified(false);
            }
        }
        return otpDtoResponse;
    }

    @Override
    public UserDTOResponse registerVerifiedUser(UserDTORequest userDTORequest) throws EmailExistException {
        validateEmailUniqueness(userDTORequest.getEmail());

        return null;
    }

    private int generateOTP() {
        return new Random().nextInt(900000) + 100000;
    }

    private void validateEmailUniqueness(String email) throws EmailExistException {
        if (isEmailAlreadyPresented(email)) {
            log.error("User: " + email + " already exists.");
            throw new EmailExistException("User with this email already exist");
        }
    }

    private boolean isEmailAlreadyPresented(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    private boolean isOTPExpired(EmailOTP emailOTP) {
        return Duration.between(emailOTP.getCreationTime(), LocalDateTime.now()).toMinutes() > OTP_EXPIRATION_MINUTES;
    }
}
