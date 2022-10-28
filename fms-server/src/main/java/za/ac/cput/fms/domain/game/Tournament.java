package za.ac.cput.fms.domain.game;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import za.ac.cput.fms.domain.info.TeamStats;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tournament")
public class Tournament {
    @Id
    @Column(name = "tournament_id", nullable = false)
    private String id;
    private String tournamentName;
    private int numberOfTeams; //this auto updates when a team is added to tournament

    /*
    ** todo: Suggestion: Add another tournament attr
    *
        Add another attribute: maxNumberOfTeams. int numberOfTeams increments auto increment as a team is added
        to the tournament, int maxNumberOfTeams determines how many teams can be added in the tournament, this variable
        is set went creating a new tournament.
    * */
    private int maxNumberOfTeams;

    private int status; //0 = created, 1 = in progress (once all teams are added) , 2 = completed

    @ManyToMany
    @JoinTable(name = "tournament_team",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> assignedTeams = new HashSet();

    @ManyToMany
    @JoinTable(name = "tournament_fixture",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "fixture_id")
    )
    private Set<Fixture> tournamentFixtures = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tournament_team_stats",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_stats_id")
    )
    private Set<TeamStats> tournamentTeamStats = new HashSet<>();

    public Tournament(Builder builder) {
        this.id = builder.id;
        this.tournamentName = builder.tournamentName;
        this.numberOfTeams = builder.numberOfTeams;
        this.maxNumberOfTeams = builder.maxNumberOfTeams;
    }

    public Tournament() {

    }

    public static class Builder{
        private String id;
        private String tournamentName;
        private int numberOfTeams;
        private int maxNumberOfTeams;

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