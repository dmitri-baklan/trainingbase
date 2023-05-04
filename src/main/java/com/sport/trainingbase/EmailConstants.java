package com.sport.trainingbase;

public interface EmailConstants {
    String SIMPLE_MAIL_TRANSFER_PROTOCOL = "smtps";
    String USERNAME = "dld.llc.production@gmail.com";
    String PASSWORD = "1qa1qa1qa";
    String FROM_EMAIL = "support@dld.com";
    String CC_EMAIL = "";
    String EMAIL_SUBJECT = "DLD9293, LLC - OTP";
    Integer OTP_EXPIRATION_MINUTES = 2;
    String GMAIL_SMTP_SERVER = "smtp.gmail.com";
    String SMTP_HOST = "mail.smtp.host";
    String SMTP_AUTH = "mail.smtp.auth";
    String SMTP_PORT = "mail.smtp.port";
    int DEFAULT_PORT = 465;
    String SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    String SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
}
