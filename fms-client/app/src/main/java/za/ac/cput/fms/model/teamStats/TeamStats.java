package za.ac.cput.fms.model.teamStats;

import java.util.HashSet;
import java.util.Set;

import za.ac.cput.fms.model.tournament.Tournament;

public class TeamStats {

    private String id;
    private String teamId;
    private String teamName;
    private int points;
    private int gamesPlayed;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;

    private Set<Tournament> tournaments = new HashSet<>();

    public TeamStats(){}

    public TeamStats(Builder builder) {
        this.id = builder.id;
        this.teamId = builder.teamId;
        this.teamName = builder.teamName;
        this.points = builder.points;
        this.gamesPlayed = builder.gamesPlayed;;
        this.goalsFor = builder.goalsFor;
        this.goalsAgainst = builder.goalsAgainst;
        this.goalDifference = builder.goalDifference;
    }

    public String getId() {
        return id;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getPoints() {
        return points;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public static class Builder{

        private String id;
        private String teamId;
        private String teamName;
        private int points;
        private int gamesPlayed;
        private int goalsFor;
        private int goalsAgainst;
        private int goalDifference;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTeamId(String teamId) {
            this.teamId = teamId;
            return this;
        }

        public Builder setTeamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Builder setPoints(int points) {
            this.points = points;
            return this;
        }

        public Builder setGamesPlayed(int gamesPlayed) {
            this.gamesPlayed = gamesPlayed;
            return this;
        }

        public Builder setGoalsFor(int goalsFor) {
            this.goalsFor = goalsFor;
            return this;
        }

        public Builder setGoalsAgainst(int goalsAgainst) {
            this.goalsAgainst = goalsAgainst;
            return this;
        }

        public Builder setGoalDifference(int goalDifference) {
            this.goalDifference = goalDifference;
            return this;
        }

        public Builder copy(TeamStats teamStats){
            this.id = teamStats.id;
            this.teamId = teamStats.teamId;
            this.teamName = teamStats.teamName;
            this.points = teamStats.points;
            this.gamesPlayed = teamStats.gamesPlayed;
            this.goalsFor = teamStats.goalsFor;
            this.goalsAgainst = teamStats.goalsAgainst;
            this.goalDifference = teamStats.goalDifference;
            return this;
        }

        public TeamStats build(){
            return new TeamStats(this);
        }

    }
}