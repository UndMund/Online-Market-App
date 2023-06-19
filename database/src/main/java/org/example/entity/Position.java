package org.example.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(of = "positionName")
@ToString(exclude = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String positionName;
    @Builder.Default
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
    @Transient
    private static List<Position> positions = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
        user.setPosition(this);
    }
}