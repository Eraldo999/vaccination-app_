package com.example.vaccination_app.model;

import com.example.vaccination_app.model.enums.AnswersStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "answers")
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "answer1")
    @NotBlank(message = "Field empty")
    private String answer1;

    @Column(name = "answer2")
    @NotBlank(message = "Field empty")
    private String answer2;

    @Column(name = "answer3")
    @NotBlank(message = "Field empty")
    private String answer3;

    @Column(name = "answer4")
    @NotBlank(message = "Field empty")
    private String answer4;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private AnswersStatus status;

}
