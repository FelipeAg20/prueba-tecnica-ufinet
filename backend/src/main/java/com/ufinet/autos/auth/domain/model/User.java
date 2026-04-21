package com.ufinet.autos.auth.domain.model;

public class User {

    private Long id;
    private String name;
    private String email;
    private String password;

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User create(String name, String email, String encodedPassword) {
        return new User(null, name, email, encodedPassword);
    }

    public Long getId()       { return id; }
    public String getName()   { return name; }
    public String getEmail()  { return email; }
    public String getPassword() { return password; }
}
