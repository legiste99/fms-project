package za.ac.cput.fms.factory.personal;

import za.ac.cput.fms.domain.personal.Manager;
import za.ac.cput.fms.util.Helper;

public class ManagerFactory {
    public static Manager newManager(String firstName, String middleName, String lastName, int age, int yoe){
        String id = "mn-"+ Helper.generateId();
        return new Manager.Builder()
                .setId(id)
                .setFirstName(firstName)
                .setMiddleName(middleName)
                .setLastName(lastName)
                .setAge(age)
                .setYearsOfExperience(yoe)
                .build();
    }
}
