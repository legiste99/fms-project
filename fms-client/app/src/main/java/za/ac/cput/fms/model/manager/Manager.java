package za.ac.cput.fms.model.manager;

import java.util.HashSet;
import java.util.Set;

import za.ac.cput.fms.model.team.Team;

public class Manager {

    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private int age; // todo: Manager - can change this to date of birth and get age from that.
    private int yearsOfExperience;

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

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public Set<Team> getTeamSet() {
        return teamSet;
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
