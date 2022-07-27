package com.example.vaccination_app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "question1")
    private String question1;

    @Column(name = "question2")
    private String question2;

    @Column(name = "question3")
    private String question3;

    @Column(name = "question4")
    private String question4;
}
