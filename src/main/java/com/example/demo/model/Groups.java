package com.example.demo.model;

import lombok.*;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Users owner;

    public Groups(String name, Users owner) {
        this.name = name;
        this.owner = owner;
    }
}
