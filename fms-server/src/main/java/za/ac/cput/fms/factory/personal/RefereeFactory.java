package za.ac.cput.fms.factory.personal;

import za.ac.cput.fms.domain.personal.Referee;
import za.ac.cput.fms.util.Helper;

public class RefereeFactory {
    public static Referee newManager(String firstName, String middleName, String lastName, int age, int yoe){
        String id = "mn-"+ Helper.generateId();
        return new Referee.Builder()
                .setId(id)
                .setFirstName(firstName)
                .setMiddleName(middleName)
                .setLastName(lastName)
                .setAge(age)
                .setYearsOfExperience(yoe)
                .build();
    }
}
