package com.databaseproject.cinedev.models.base;

import com.databaseproject.cinedev.models.base.compositeKey.UserRoleId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "roles"})
public class UserRoles {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roles")
    @JoinColumn(name = "rol_id", nullable = false)
    private Roles roles;
}
