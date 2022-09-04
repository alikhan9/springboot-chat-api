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
@IdClass(GroupMembersId.class)
public class GroupMembers  implements Serializable {

    @Id
    private Long group_id;

    @Id
    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @MapsId("group_id")
    private Groups group;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("user_id")
    private Users user;

    public GroupMembers(Long group_id, Long user_id) {
        this.group_id = group_id;
        this.user_id = user_id;
    }
}
