package za.ac.cput.fms.domain.personal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import za.ac.cput.fms.domain.game.Fixture;
import za.ac.cput.fms.domain.game.Team;
import za.ac.cput.fms.domain.info.PlayerStats;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "player")
public class Player {
    @Id
    @Column(name = "player_id", nullable = false)
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private int age; // todo: Player - can change this to date of birth and get age from that.
    private String position;
    private int positionNumber;

    // TODO: These attributes should be in their own Class PLayerGlobalStats
    private int totalGoalsScored;
    private int totalAssistsMade;

    //OneToMany - Player only belongs to one team
    @JsonIgnore
    @ManyToMany(mappedBy = "teamPlayers")
    private Set<Team> team = new HashSet<>();

    //This should be a OneToMany Relationship
    @ManyToMany(mappedBy = "fixturePlayerStats")
    private Set<Fixture> playerFixtures = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "player_player_stats",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "player_stats_id")
    )
    private Set<PlayerStats> playerPlayerStats = new HashSet<>();

    public Player(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.position = builder.position;
        this.positionNumber = builder.positionNumber;
        this.totalGoalsScored = builder.totalGoalsScored;
        this.totalAssistsMade = builder.totalAssistsMade;
    }

    public Player() {

    }

    public static class Builder{

        private String id;
        private String firstName;
        private String middleName;
        private String lastName;
        private int age;
        private String position;
        private int positionNumber;
        private int totalGoalsScored;
        private int totalAssistsMade;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setPosition(String position) {
            this.position = position;
            return this;
        }

        public Builder setPositionNumber(int positionNumber) {
            this.positionNumber = positionNumber;
            return this;
        }

        public Builder setTotalGoalsScored(int totalGoalsScored) {
            this.totalGoalsScored = totalGoalsScored;
            return this;
        }

        public Builder setTotalAssistsMade(int totalAssistsMade) {
            this.totalAssistsMade = totalAssistsMade;
            return this;
        }

        public Builder copy(Player player){
            this.id = player.id;
            this.firstName = player.firstName;
            this.middleName = player.middleName;
            this.lastName = player.lastName;
            this.age = player.age;
            this.position = player.position;
            this.positionNumber = player.positionNumber;
            this.totalGoalsScored = player.totalGoalsScored;
            this.totalAssistsMade = player.totalAssistsMade;
            return this;
        }

        public Player build(){
            return new Player(this);
        }

    }
}