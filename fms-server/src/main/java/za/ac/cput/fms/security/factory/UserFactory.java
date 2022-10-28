package za.ac.cput.fms.security.factory;

import za.ac.cput.fms.security.domain.User;
import za.ac.cput.fms.util.Helper;

public class UserFactory {

    public static User newUser(String email, String firstName, String lastName, String password, String role){
        String id = "user-"+ Helper.generateId();

        return new User.Builder()
                .setId(id)
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setRole(role)
                .build();
    }
}
