package com.bist.backendmodule.modules.group.models;

import com.bist.backendmodule.modules.user.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a group.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_GROUP")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    /**
     * Method to remove this group from users before deletion.
     * Ensures that the group is removed from the users' group lists
     * before the group itself is deleted.
     */
    @PreRemove
    private void removeGroupFromUser(){
        for (User user : users){
            user.getGroups().remove(this);
        }
    }
}
