package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private long id;
    @NotEmpty
    public String firstName;
    @NotEmpty
    public String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    public String email;
    @NotEmpty(message = "Password should not be empty")
    public String password;

    private List<Long> rolesIds;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rolesIds=" + rolesIds +
                '}';
    }
}
