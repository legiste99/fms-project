package za.ac.cput.fms.model.referee;

public class Referee {

    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private int age;  // todo: Referee - can change this to date of birth and get age from that.
    private int yearsOfExperience;

    public Referee() {
    }

    public Referee(Builder builder) {
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

        public Builder copy(Referee referee){
            this.id = referee.id;
            this.age = referee.age;
            this.firstName = referee.firstName;
            this.middleName = referee.middleName;
            this.lastName = referee.lastName;
            this.yearsOfExperience = referee.yearsOfExperience;
            return this;
        }

        public Referee build(){
            return new Referee(this);
        }
    }
}