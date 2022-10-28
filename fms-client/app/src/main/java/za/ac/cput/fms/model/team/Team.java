package za.ac.cput.fms.model.team;

import java.util.HashSet;
import java.util.Set;

import za.ac.cput.fms.model.fixture.Fixture;
import za.ac.cput.fms.model.manager.Manager;
import za.ac.cput.fms.model.player.Player;
import za.ac.cput.fms.model.tournament.Tournament;
import za.ac.cput.fms.model.venue.Venue;

public class Team {

    private String id;
    private String teamName;
    private int titlesWon;

    private Set<Tournament> tournamentSet  = new HashSet<>();

    private Set<Manager> teamManager = new HashSet<>();

    private Set<Player> teamPlayers = new HashSet<>();

    private Set<Venue> teamHomeVenue = new HashSet<>();

    private Set<Fixture> fixtureSet = new HashSet<>();

    public Team(Builder builder) {
        this.id = builder.id;
        this.teamName = builder.teamName;
        this.titlesWon = builder.titlesWon;
    }

    public Team() {

    }

    public String getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTitlesWon() {
        return titlesWon;
    }

    public Set<Tournament> getTournamentSet() {
        return tournamentSet;
    }

    public Set<Manager> getTeamManager() {
        return teamManager;
    }

    public Set<Player> getTeamPlayers() {
        return teamPlayers;
    }

    public Set<Venue> getTeamHomeVenue() {
        return teamHomeVenue;
    }

    public Set<Fixture> getFixtureSet() {
        return fixtureSet;
    }

    public static class Builder{
        private String id;
        private String teamName;
        private int titlesWon;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTeamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Builder setTitlesWon(int titlesWon) {
            this.titlesWon = titlesWon;
            return this;
        }

        public Builder copy(Team team){
            this.id = team.id;
            this.teamName = team.teamName;
            this.titlesWon = team.titlesWon;
            return this;
        }

        public Team build(){
            return new Team(this);
        }
    }

}
