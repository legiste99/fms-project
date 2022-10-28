package za.ac.cput.fms.domain.personal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import za.ac.cput.fms.domain.game.Team;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "manager")
public class Manager {

    @Id
    @Column(name = "manager_id", nullable = false)
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private int age; // todo: Manager - can change this to date of birth and get age from that.
    private int yearsOfExperience;

    /*@OneToOne(mappedBy = "manager")
    private Team team;*/

    /*@OneToOne
    @JoinTable(name = "team_manager",
            joinColumns = @JoinColumn(name = "manager_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )*/

    @JsonIgnore
    @ManyToMany(mappedBy = "teamManager")
    private Set<Team> teamSet  = new HashSet<>();

    public Manager(){}

    public Manager(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.yearsOfExperience = builder.yearsOfExperience;
    }

    public static class Builder{

        private String id;
        private String firstName;
        private String middleName;
        private String lastName;
        private int age;
        private int yearsOfExperience;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setYearsOfExperience(int yearsOfExperience) {
            this.yearsOfExperience = yearsOfExperience;
            return this;
        }

        public Builder copy(Manager manager){
            this.id = manager.id;
            this.age = manager.age;
            this.firstName = manager.firstName;
            this.middleName = manager.middleName;
            this.lastName = manager.lastName;
            this.yearsOfExperience = manager.yearsOfExperience;
            return this;
        }

        public Manager build(){
            return new Manager(this);
        }
    }
}
