package za.ac.cput.fms.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "venue")
public class Venue {
    @Id
    @Column(name = "venue_id", nullable = false)
    private String id;
    private String venueName;
    private int capacity;


    //Should be a OneToOne Relationship, A team can only have one Venue and a Venue can belong to only one Team.
    @JsonIgnore
    @ManyToMany(mappedBy = "teamHomeVenue")
    private Set<Team> team = new HashSet<>();

    public Venue(Builder builder) {
        this.id = builder.id;
        this.venueName = builder.venueName;
        this.capacity = builder.capacity;
    }

    public Venue() {
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