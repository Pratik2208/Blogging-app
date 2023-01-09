package com.bloggingapp.payloads;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 4,message = "username must be of min 4 characters !!")
    private String name;

    @Email(message = "Email Address is not valid !!")
    private String email;

    @NotEmpty
    @Size(min = 3,max = 10,message = "Password must be min of 3 and max of 10 characters !!")
    private String password;

    @NotEmpty
    private String about;
}
