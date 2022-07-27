package com.example.vaccination_app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AnswerCreateDto {

    @NotBlank
    private String answer;
}
