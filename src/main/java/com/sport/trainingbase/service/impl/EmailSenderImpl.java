package com.sport.trainingbase.service.impl;

import com.sport.trainingbase.service.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import static com.sport.trainingbase.EmailConstants.EMAIL_SUBJECT;

@Slf4j
@Service("emailSender")
@Transactional
public class EmailSenderImpl implements EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendOTPToEmail(String email, Integer otp) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email);

        helper.setSubject(EMAIL_SUBJECT);

        helper.setText(String.format(
                "Welcome, to TrainingBase!<br><br>Your otp:</b>%s</b><br><br><br><br><br>The Support Team", otp), true);
        javaMailSender.send(msg);
    }
}
