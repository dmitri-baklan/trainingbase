package com.sport.trainingbase.service;

import com.sport.trainingbase.domain.dto.request.EmailOTPDTORequest;
import com.sport.trainingbase.domain.dto.request.UserDTORequest;
import com.sport.trainingbase.domain.dto.response.EmailOTPDTOResponse;
import com.sport.trainingbase.domain.dto.response.UserDTOResponse;
import com.sport.trainingbase.exception.EmailExistException;
import com.sport.trainingbase.exception.EmailNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails getCurrentUser();

    public String saveEmailAndSendOTP(String email)throws EmailExistException;

    public EmailOTPDTOResponse verifyEmailWithOTP(EmailOTPDTORequest otpRequest) throws EmailNotFoundException;

    public UserDTOResponse registerVerifiedUser(UserDTORequest userDTORequest) throws EmailExistException;

}
