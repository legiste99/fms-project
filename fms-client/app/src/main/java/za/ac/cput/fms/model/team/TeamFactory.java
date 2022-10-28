package za.ac.cput.fms.model.team;

import za.ac.cput.fms.util.Helper;

public class TeamFactory {
    public static Team newTeam(String name){
        String id = "tm-"+ Helper.generateId();
        return new Team.Builder()
                .setId(id)
                .setTeamName(name)
                .build();
    }
}
