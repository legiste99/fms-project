package za.ac.cput.fms.factory.game;

import za.ac.cput.fms.domain.game.Team;
import za.ac.cput.fms.util.Helper;

public class TeamFactory {

    public Team newTeam(String name, int titlesWon){

        String id = "tm-"+ Helper.generateId();

        return new Team.Builder()
                .setId(id)
                .setTeamName(name)
                .setTitlesWon(titlesWon)
                .build();
    }
}
