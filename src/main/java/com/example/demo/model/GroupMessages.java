package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "sender")
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private Groups receiver;

    private Timestamp date;

    public GroupMessages(String message, Users sender, Groups receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        date = Timestamp.from(Instant.now());
    }

    public GroupMessages(String message, Users sender, Groups receiver, Timestamp date) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
    }
}
