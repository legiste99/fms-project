package za.ac.cput.fms.model.fixture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import za.ac.cput.fms.model.gameStats.GameStats;
import za.ac.cput.fms.model.playerStats.PlayerStats;
import za.ac.cput.fms.model.referee.Referee;
import za.ac.cput.fms.model.team.Team;
import za.ac.cput.fms.model.venue.Venue;

public class Fixture {

    private String id;
    private int status; // 0=created, 1=pending(when only 1 team is added) 2=in progress, 3=complete
    private final int maxTeams = 2;
    private String teamAId;
    private String teamBid;
    private String teamAName;
    private String teamBName;

    private Set<Team> fixtureTeams = new HashSet<>();

    private Set<PlayerStats> fixturePlayerStats = new HashSet<>();

    private Set<GameStats> fixtureGameStats = new HashSet<>();

    private List<Referee> fixtureReferee = new ArrayList<>();

    private List<Venue> fixtureVenue = new ArrayList<>();
    /*
    private Set<Referee> fixtureReferee = new HashSet<>();

    private Set<Venue> fixtureVenue = new HashSet<>();
*/
    public Fixture(Builder builder) {
        this.id = builder.id;
        this.teamAId = builder.teamAId;
        this.teamBid = builder.teamBid;
        this.status = builder.status;
        this.teamAName = builder.teamAName;
        this.teamBName = builder.teamBName;
    }

    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public String getTeamAId() {
        return teamAId;
    }

    public String getTeamBid() {
        return teamBid;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public Set<Team> getFixtureTeams() {
        return fixtureTeams;
    }

    public Set<PlayerStats> getFixturePlayerStats() {
        return fixturePlayerStats;
    }

    public Set<GameStats> getFixtureGameStats() {
        return fixtureGameStats;
    }

    public List<Referee> getFixtureReferee() {
        return fixtureReferee;
    }

    public List<Venue> getFixtureVenue() {
        return fixtureVenue;
    }

    /*


    public Set<Referee> getFixtureReferee() {
        return fixtureReferee;
    }

    public Set<Venue> getFixtureVenue() {
        return fixtureVenue;
    }
*/

    public static class Builder{

        private String id;
        private int status; // 0=created, 1=pending(when only 1 team is added) 2=in progress, 3=complete
        private String teamAId;
        private String teamBid;
        private String teamAName;
        private String teamBName;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setTeamAId(String teamAId) {
            this.teamAId = teamAId;
            return this;
        }

        public Builder setTeamBid(String teamBid) {
            this.teamBid = teamBid;
            return this;
        }

        public Builder setTeamAName(String teamAName) {
            this.teamAName = teamAName;
            return this;
        }

        public Builder setTeamBName(String teamBName) {
            this.teamBName = teamBName;
            return this;
        }

        public Builder copy(Fixture fx){
            this.id = fx.id;
            this.status = fx.status;
            return this;
        }

        public Fixture build(){
            return new Fixture(this);
        }
    }

}
