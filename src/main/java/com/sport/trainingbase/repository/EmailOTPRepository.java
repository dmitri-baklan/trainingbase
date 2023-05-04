package com.sport.trainingbase.repository;

import com.sport.trainingbase.domain.model.user.EmailOTP;
import com.sport.trainingbase.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailOTPRepository extends JpaRepository<EmailOTP, Long> {
    Optional<EmailOTP> findEmailOTPById(Long aLong);
    Optional<EmailOTP> findEmailOTPByEmail(String email);
}
