package dev.wegner.academy_app.user_management;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = AcademyUser.TABLE_NAME)
public class AcademyUser implements Serializable {

    private static final long serialVersionUID = 2L;

    public static final String TABLE_NAME = "academy_user";

    public static final String ID_COLUMN = "id";
    public static final String USERNAME_COLUMN = "username";
    public static final String PASSWORD_HASH_COLUMN = "password_hash";
    public static final String ENABLED_COLUMN = "enabled";
    public static final String ROLE_COLUMN = "role";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN)
    private Long id;

    @Column(name = USERNAME_COLUMN, nullable = false, unique = true)
    private String username;

    @Column(name = PASSWORD_HASH_COLUMN, nullable = false)
    private String passwordHash;

    @Column(name = ENABLED_COLUMN, nullable = false)
    private boolean isEnabled;

    @Enumerated(EnumType.STRING)
    @Column(name = ROLE_COLUMN, nullable = false)
    private AcademyRole role;

    protected AcademyUser() {
        // JPA
    }

    private AcademyUser(String username, String passwordHash, boolean isEnabled, AcademyRole role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.isEnabled = isEnabled;
        this.role = role;
    }

    public static AcademyUser create(String username, String passwordHash, boolean isEnabled, AcademyRole role) {
        return new AcademyUser(username, passwordHash, isEnabled, role);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public AcademyRole getRole() {
        return role;
    }
}
