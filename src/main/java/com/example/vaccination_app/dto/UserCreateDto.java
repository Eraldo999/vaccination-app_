package com.example.vaccination_app.dto;

import com.example.vaccination_app.model.VaccinationCenter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCreateDto {


    @NotEmpty(message = "Field empty")
    private String name;

    @NotEmpty(message = "Field empty")
    private String surname;

    @NotEmpty(message = "Error")
    @Email
    private String email;

    @NotEmpty(message = "Field empty")
    private String password;

    @NotEmpty(message = "Field empty")
    private String idCard;

    private String address;

    @NotNull
    private long vaccinationCenterId;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
