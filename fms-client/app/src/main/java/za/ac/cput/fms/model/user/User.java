package za.ac.cput.fms.model.user;

public class User {

    private String id;
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

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
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