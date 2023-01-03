package ru.java.securityjwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull(message = "Username cannot be empty")
    private String username;
    @NotNull(message = "Password cannot be empty")
    private String password;
    @NotNull(message = "Address cannot be empty")
    private String address;
    @NotNull(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Phone number cannot be empty")
    private Long phone;
    @NotNull(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "PAN Number cannot be empty")
    private String pan;
    @NotNull(message = "Account Type cannot be empty")
    private String type;
    @NotNull(message = "State cannot be empty")
    private String state;
    @NotNull(message = "Country cannot be empty")
    private String country;
    @NotNull(message = "Date of birth cannot be empty")
    private String dob;
}
