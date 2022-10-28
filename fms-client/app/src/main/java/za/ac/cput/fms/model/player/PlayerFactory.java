package za.ac.cput.fms.model.player;

import za.ac.cput.fms.util.Helper;

public class PlayerFactory {
    public static Player newPlayer(String fName, String mName, String lName, int age, String position, int positionNumber){
        String id = "pl-"+ Helper.generateId();
        return new Player.Builder()
                .setId(id)
                .setFirstName(fName)
                .setMiddleName(mName)
                .setLastName(lName)
                .setAge(age)
                .setPosition(position)
                .setPositionNumber(positionNumber)
                .build();
    }
}