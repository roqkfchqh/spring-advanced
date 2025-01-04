package org.example.expert.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.presentation.utils.AuthUser;
import org.example.expert.domain.common.Timestamped;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public static User of(String email, String password, UserRole userRole) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.userRole = userRole;
        return user;
    }

    public static User fromAuthUser(AuthUser authUser) {
        User user = new User();
        user.id = authUser.id();
        user.email = authUser.email();
        user.userRole = authUser.userRole();
        return user;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
