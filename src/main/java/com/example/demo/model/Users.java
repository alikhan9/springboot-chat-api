package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;
    @NotBlank(message = "Username may not be null")
    private String username;
    @NotBlank(message = "First name may not be null")
    private String first_name;
    @NotBlank(message = "Last name may not be null")
    private String last_name;
    @Email
    @NotBlank(message = "Email may not be null")
    private String email;

    private LocalDate dateofbirth;

    private Boolean is_admin;

    @NotBlank(message = "Password may not be null")
    @JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private String password;


    public Users(String username, String first_name, String last_name, String email, LocalDate dateofbirth, String password) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.dateofbirth = dateofbirth;
        this.password = password;
    }


    @Override
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        if(is_admin)
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}



