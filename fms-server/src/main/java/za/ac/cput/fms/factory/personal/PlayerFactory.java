package za.ac.cput.fms.factory.personal;

import za.ac.cput.fms.domain.personal.Player;
import za.ac.cput.fms.util.Helper;

public class PlayerFactory {
    public static Player newPlayer(String fName, String mName, String lName, int age, String position, int positionNumber, int totalGoals, int totalAssistsMade){
        String id = "mn-"+ Helper.generateId();
        return new Player.Builder()
                .setId(id)
                .setFirstName(fName)
                .setMiddleName(mName)
                .setLastName(lName)
                .setAge(age)
                .setPosition(position)
                .setPositionNumber(positionNumber)
                .setTotalGoalsScored(totalGoals)
                .setTotalAssistsMade(totalAssistsMade)
                .build();
    }
}
