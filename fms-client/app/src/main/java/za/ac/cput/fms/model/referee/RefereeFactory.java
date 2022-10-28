package za.ac.cput.fms.model.referee;

import za.ac.cput.fms.util.Helper;

public class RefereeFactory {
    public static Referee newReferee(String firstName, String middleName, String lastName, int age, int yoe){
        String id = "rf-"+ Helper.generateId();
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
