package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

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
public class Position implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String positionName;
    @Builder.Default
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
        user.setPosition(this);
    }

    @Override
    public String getAuthority() {
        return this.getPositionName();
    }
}