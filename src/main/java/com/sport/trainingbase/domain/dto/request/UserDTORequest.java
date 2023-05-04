package com.sport.trainingbase.domain.dto.request;

import com.sport.trainingbase.domain.model.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTORequest {
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_REGEX = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!=@$%^&*-]).{8,}";

    @NotBlank(message = "Should not be empty!")
    @Pattern(regexp = EMAIL_REGEX, message = "Wrong email format." )
    @Size(max = 30, message = "Email should contain less then 30 symbols.")
    private String email;

    @NotBlank(message = "Should not be empty!")
    @Pattern(regexp = PASSWORD_REGEX, message = "Password should contain at least 8 symbols, one number, both register and one special symbol [#?!@$%^&*-]")
    private String password;

    @NotBlank(message = "Should not be empty!")
    @Size(max = 30)
    private String name;

    @NotNull
    private Gender gender;

    @Max(value = 1000)
    @Min(value = 1)
    private Long weight;
}
