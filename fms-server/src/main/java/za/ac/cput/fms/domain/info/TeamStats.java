package za.ac.cput.fms.domain.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import za.ac.cput.fms.domain.game.Tournament;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "team_stats")
public class TeamStats {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    private String teamId;
    private String teamName;
    private String tournamentId;
    private int points;
    private int gamesPlayed;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;

    // Not Necessary
    @JsonIgnore
    @ManyToMany(mappedBy = "tournamentTeamStats")
    private Set<Tournament> tournaments = new HashSet<>();

    public TeamStats(){}

    public TeamStats(Builder builder) {
        this.id = builder.id;
        this.teamId = builder.teamId;
        this.tournamentId = builder.tournamentId;
        this.teamName = builder.teamName;
        this.points = builder.points;
        this.gamesPlayed = builder.gamesPlayed;;
        this.goalsFor = builder.goalsFor;
        this.goalsAgainst = builder.goalsAgainst;
        this.goalDifference = builder.goalDifference;
    }

    public static class Builder{

        private String id;
        private String teamId;
        private String tournamentId;
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

        public Builder setTournamentId(String tournamentId) {
            this.tournamentId = tournamentId;
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
            this.tournamentId = tournamentId;
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