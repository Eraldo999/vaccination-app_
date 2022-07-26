package com.example.vaccination_app.model;

import com.example.vaccination_app.config.SecurityConfig;
import com.example.vaccination_app.dto.UserCreateDto;
import com.example.vaccination_app.dto.UserUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "application_role_id", nullable = false)
    private ApplicationRole applicationRole;

    @OneToMany(mappedBy = "user")
    private Set<Booking> bookings = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "vaccination_center_id", nullable = false)
    private VaccinationCenter vaccinationCenter;

    @OneToMany(mappedBy = "user")
    private Set<Notification> notifications;

    public User(long id, String name, String surname, String email, String password, String idCard, String address, ApplicationRole applicationRole) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.idCard = idCard;
        this.address = address;
        this.applicationRole = applicationRole;
    }

    public static User fromCreateRequest(UserCreateDto req) {
        var usr = new User();
        usr.setName(req.getName());
        usr.setSurname(req.getSurname());
        usr.setEmail(req.getEmail());
        usr.setPassword(SecurityConfig.PASSWORD_ENCODER.encode(req.getPassword()));
        usr.setIdCard(req.getIdCard());
        usr.setAddress(req.getAddress());
        return usr;
    }

    public void update(UserUpdateDto req) {
        name = req.getName();
        surname = req.getSurname();
        email = req.getEmail();
        password = req.getPassword();
        idCard = req.getIdCard();
        address = req.getAddress();
    }

}
