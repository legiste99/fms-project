package za.ac.cput.fms.model.tournament;

import java.util.HashSet;
import java.util.Set;

import za.ac.cput.fms.model.fixture.Fixture;
import za.ac.cput.fms.model.team.Team;
import za.ac.cput.fms.model.teamStats.TeamStats;

public class Tournament {
    private String id;
    private String tournamentName;
    private int numberOfTeams;
    private int maxNumberOfTeams;
    private int status;

    private Set<Team> assignedTeams = new HashSet();

    private Set<Fixture> tournamentFixtures = new HashSet<>();

    private Set<TeamStats> tournamentTeamStats = new HashSet<>();

    public Tournament(Builder builder) {
        this.id = builder.id;
        this.tournamentName = builder.tournamentName;
        this.numberOfTeams = builder.numberOfTeams;
        this.maxNumberOfTeams = builder.maxNumberOfTeams;
        this.status = builder.status;
    }

    public Tournament() {

    }

    public String getId() {
        return id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public int getMaxNumberOfTeams() {
        return maxNumberOfTeams;
    }

    public int getStatus() {
        return status;
    }

    public Set<Team> getAssignedTeams() {
        return assignedTeams;
    }

    public Set<Fixture> getTournamentFixtures() {
        return tournamentFixtures;
    }

    public Set<TeamStats> getTournamentTeamStats() {
        return tournamentTeamStats;
    }

    public static class Builder{
        private String id;
        private String tournamentName;
        private int numberOfTeams;
        private int maxNumberOfTeams;
        private int status;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTournamentName(String tournamentName) {
            this.tournamentName = tournamentName;
            return this;
        }

        public Builder setNumberOfTeams(int numberOfTeams) {
            this.numberOfTeams = numberOfTeams;
            return this;
        }

        public Builder setMaxNumberOfTeams(int maxNumberOfTeams) {
            this.maxNumberOfTeams = maxNumberOfTeams;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder copy(Tournament t){
            this.id = t.id;
            this.tournamentName = t.tournamentName;
            this.numberOfTeams = t.numberOfTeams;
            this.maxNumberOfTeams = t.maxNumberOfTeams;
            return this;
        }

        public Tournament build(){
            return new Tournament(this);
        }

    }

}