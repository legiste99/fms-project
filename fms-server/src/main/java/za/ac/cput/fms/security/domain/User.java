package za.ac.cput.fms.security.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String role;

    public User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.password = builder.password;
        this.role = builder.role;
    }

    public User(){

    }

    public static class Builder{

        private String id;
        private String email;
        private String firstName;
        private String lastName;
        private String password;
        private String role;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Builder copy(User user){
            this.id = user.id;
            this.email = user.email;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.password = user.password;
            this.role = user.role;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }

}