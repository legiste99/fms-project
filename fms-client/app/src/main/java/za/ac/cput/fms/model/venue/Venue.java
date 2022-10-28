package za.ac.cput.fms.model.venue;

import java.util.HashSet;
import java.util.Set;

import za.ac.cput.fms.model.team.Team;

public class Venue {

    private String id;
    private String venueName;
    private int capacity;

    private Set<Team> team = new HashSet<>();

    public Venue(Builder builder) {
        this.id = builder.id;
        this.venueName = builder.venueName;
        this.capacity = builder.capacity;
    }

    public Venue() {
    }

    public String getId() {
        return id;
    }

    public String getVenueName() {
        return venueName;
    }

    public int getCapacity() {
        return capacity;
    }

    public Set<Team> getTeam() {
        return team;
    }

    public static class Builder{

        private String id;
        private String venueName;
        private int capacity;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setVenueName(String venueName) {
            this.venueName = venueName;
            return this;
        }

        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder copy(Venue venue){
            this.id = venue.id;
            this.venueName = venueName;
            this.capacity = venue.capacity;
            return this;
        }

        public Venue build(){
            return new Venue(this);
        }

    }
}