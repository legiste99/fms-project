package za.ac.cput.fms.factory.game;


import za.ac.cput.fms.domain.game.Tournament;
import za.ac.cput.fms.util.Helper;

public class TournamentFactory {
    public static Tournament newTournament(String name, int maxTeams){
        String id = "tn-"+ Helper.generateId();
        return new Tournament.Builder()
                .setId(id)
                .setTournamentName(name)
                .setMaxNumberOfTeams(maxTeams)
                .build();
    }
}
