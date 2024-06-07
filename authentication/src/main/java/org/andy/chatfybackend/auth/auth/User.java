package org.andy.chatfybackend.auth.auth;

public class User {
    private final String username;
    private final String email;
    private final Long id;
    private final Role role;


    public User(String username, String email, Long id,
                Role role) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", role=" + role +
                '}';
    }
}
