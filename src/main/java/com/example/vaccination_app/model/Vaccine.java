package com.example.vaccination_app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vaccine")
@Getter
@Setter
@NoArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "vaccine_type")
    private String vaccineType;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "details")
    private String details;

    @OneToMany(mappedBy = "vaccine")
    private Set<Booking> bookings = new HashSet<>();
}
