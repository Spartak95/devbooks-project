package am.devbooks.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 3, max = 15, message = "{val.usernameSize}")
    @Pattern(regexp = "([a-zA-Z])+", message = "{val.usernameRegExp}")
    private String username;

    @Email(message = "{val.email}")
    @NotEmpty(message = "{val.emailNotEmpty}")
    private String email;

    @Size(min = 8, max = 120, message = "{val.passSize}")
    //@Pattern(regexp = "([a-zA-Z])+", message = "{val.passRegExp}")
    private String password;

    private String role;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
}
