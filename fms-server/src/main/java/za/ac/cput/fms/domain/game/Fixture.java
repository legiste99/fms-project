package za.ac.cput.fms.domain.game;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import za.ac.cput.fms.domain.info.GameStats;
import za.ac.cput.fms.domain.info.PlayerStats;
import za.ac.cput.fms.domain.personal.Referee;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "fixture")
public class Fixture {
    @Id
    @Column(name = "fixture_id", nullable = false)
    private String id;
    private int status; // 0=create, 1=pending(when only 1 team is added) 2=in progress, 3=complete
    private final int maxTeams = 2;
    String teamAId, teamBid;
    private String teamAName, teamBName;


    /*
            Many to many but only two teams are required in a Fixture, Client Side should make
            sure no more than 2 teams are added to a Fixture, otherwise that would just be weird.
    * */
    @ManyToMany
    @JoinTable(name = "fixture_teams",
            joinColumns = @JoinColumn(name = "fixture_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> fixtureTeams = new HashSet<>();


    //Unidirectional - Player Stats
    //This should be a ManyToOne Relationship
    //When a Team is added to a fixture, PlayerStat is created for each player in that team.
    @ManyToMany
    @JoinTable(name = "fixture_player_stats",
            joinColumns = @JoinColumn(name = "fixture_id"),
            inverseJoinColumns = @JoinColumn(name = "player_stats_id")
    )
    private Set<PlayerStats> fixturePlayerStats = new HashSet<>();

    //Unidirectional - Game Stats
    // Created when a team is assigned to a fixture. A fixture should/will only have two GameStats, one for each team in the fixture
    @ManyToMany
    @JoinTable(name = "fixture_game_stats",
            joinColumns = @JoinColumn(name = "fixture_id"),
            inverseJoinColumns = @JoinColumn(name = "game_stats_id")
    )
    private Set<GameStats> fixtureGameStats = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "fixture_referee",
            joinColumns = @JoinColumn(name = "fixture_id"),
            inverseJoinColumns = @JoinColumn(name = "referee_id")
    )
    private List<Referee> fixtureReferee = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "fixture_venue",
            joinColumns = @JoinColumn(name = "fixture_id"),
            inverseJoinColumns = @JoinColumn(name = "venue_id")
    )
    private List<Venue> fixtureVenue = new ArrayList<>();


}