package com.example.vaccination_app.dto;

import com.example.vaccination_app.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {
    private long id;

    @NotBlank(message = "First Name error")
    private String name;

    @NotBlank(message = "Last Name error")
    private String surname;

    @Email(message = "Email error")
    @NotBlank
    private String email;

    @NotNull
    private String password;

    @NotBlank(message = "Id Card error")
    private String idCard;

    @NotBlank(message = "Adress error")
    private String address;

    public static UserUpdateDto fromEntity(User usr) {
        var out = new UserUpdateDto();
        out.setId(usr.getId());
        out.setName(usr.getName());
        out.setSurname(usr.getSurname());
        out.setEmail(usr.getEmail());
        out.setPassword(usr.getPassword());
        out.setIdCard(usr.getIdCard());
        out.setAddress(usr.getAddress());

        return out;
    }

}
