package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table
@Entity
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sender")
    private Users sender;

    @OneToOne
    @JoinColumn(name = "receiver")
    private Users receiver;

    private String message;
    private Timestamp date;

    public Messages(Users sender, Users receiver, Timestamp date, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.message = message;
    }
}
