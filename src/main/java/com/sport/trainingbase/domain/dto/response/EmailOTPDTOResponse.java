package com.sport.trainingbase.domain.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailOTPDTOResponse {
    String email;
    Integer otp;
    OTPResponseStatus otpResponseStatus;
}
