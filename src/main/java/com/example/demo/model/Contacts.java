package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@IdClass(ContactsId.class)
public class Contacts implements Serializable {

    @Id
    private Long id_user1;

    @Id
    private Long id_user2;

    @ManyToOne
    @JoinColumn(name = "id_user1")
    @MapsId("id_user1")
    private Users users1;

    @ManyToOne
    @JoinColumn(name = "id_user2")
    @MapsId("id_user2")
    private Users users2;

    public Contacts(Long user1, Long user2) {
        this.id_user1 = user1;
        this.id_user2 = user2;
    }
}
