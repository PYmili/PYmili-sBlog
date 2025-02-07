package icu.pymiliblog.pymillsblog.pojo.user;

import java.util.Date;

public class UserPojo {
    private Integer id;
    private String name;
    private String passwordHash;
    private String email;
    private Date registration;
    private String salt;

    public UserPojo() {}

    public UserPojo(Integer id, String name, String passwordHash, String email, Date registration, String salt) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.registration = registration;
        this.salt = salt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistration() {
        return registration;
    }

    public void setRegistration(Date registration) {
        this.registration = registration;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "UserPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                // ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", registration=" + registration +
                // ", salt='" + salt + '\'' +
                '}';
    }
}
