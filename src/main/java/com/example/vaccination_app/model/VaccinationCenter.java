package com.example.vaccination_app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vaccination_center")
@Getter
@Setter
@NoArgsConstructor
public class VaccinationCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "vaccinationCenter_vaccine",
            joinColumns = @JoinColumn(name = "vaccination_center_id"),
            inverseJoinColumns = @JoinColumn(name = "vaccine_id")
    )
    private Set<Vaccine> vaccines = new HashSet<>();

    @OneToMany(mappedBy = "vaccinationCenter")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "vaccinationCenter")
    private Set<Booking> bookings = new HashSet<>();

}
