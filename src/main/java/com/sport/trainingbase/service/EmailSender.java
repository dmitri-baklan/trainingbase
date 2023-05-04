package com.sport.trainingbase.service;

import javax.mail.MessagingException;

public interface EmailSender {
    public void sendOTPToEmail(String email, Integer otp) throws MessagingException;

}
